package dev.hbeck.kdl;

import dev.hbeck.kdl.parse.KDLParser;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestExample {
    @Test
    public void testExample() throws IOException {
        String input = new String(Files.readAllBytes(Path.of("src/test/resources/test_cases/input/example.kdl")));
        KDLParser parser = new KDLParser();
        parser.parse(input);
    }
}
