import org.objectweb.asm.tools.Retrofitter
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.zip.Deflater

plugins {
    java
    jacoco
    `maven-publish`
    id("xyz.wagyourtail.jvmdowngrader") version("1.2.1")
}

group = "dev.hbeck.kdl"
version = "0.2.0"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hkolbeck/kdl4j")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = file("${layout.buildDirectory}/jacoco/coverage")
    }
}

dependencies {
    testImplementation("junit", "junit", "4.12")
    testImplementation("org.mockito", "mockito-core", "3.7.7")
}

/*
java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22

    withSourcesJar()
    withJavadocJar()
}
*/

// carbon copy from Nolij/ZSON, yea i like this blackmagik
// (oh that's a camellia reference lol)
tasks.downgradeJar {
    dependsOn(tasks.jar)
    downgradeTo = JavaVersion.VERSION_1_8
    archiveClassifier = "j8"

    doLast {
        val jar = archiveFile.get().asFile
        val dir = temporaryDir.resolve("downgradeJar5")
        dir.mkdirs()

        copy {
            from(zipTree(jar))
            into(dir)
        }

        Retrofitter().run {
            retrofit(dir.toPath())
            // verify(dir.toPath())
        }

        JarOutputStream(archiveFile.get().asFile.outputStream()).use { jos ->
            jos.setLevel(Deflater.BEST_COMPRESSION)
            dir.walkTopDown().forEach { file ->
                if (file.isFile) {
                    jos.putNextEntry(JarEntry(file.relativeTo(dir).toPath().toString()))
                    file.inputStream().use { it.copyTo(jos) }
                    jos.closeEntry()
                }
            }
            jos.flush()
            jos.finish()
        }
    }
}

tasks.jar {
    finalizedBy(tasks.downgradeJar)
}
