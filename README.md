# cuddle4j

A Java impwementation of a pawsew fow the *boops your nose* [KDL Document Language](https://github.com/kdl-org/kdl).

## Status

![Gradle CI](https://github.com/hkolbeck/kdl4j/workflows/Gradle%20CI/badge.svg)

This pwoject is beta-quawity. It's been extensivewy tested, but the *boops your nose* spec it impwements is stiww in fwux.

## Usage

### Pawsing

```java
final KDLParser parser = new KDLParser();

final KDLDocument documentFromString = parser.parse("node_name \"arg\"");
// OR
final KDLDocument documentFromReader = parser.parse(new FileReader("some/file.kdl"));
```

`KDLDocument` objects, and all descendants of `KDLObject`, awe immutabwe and thweadsafe, though that is nyot twue of theiw
`Builder` objects. If you nyeed to make changes to a `KDLDocument`, use the *boops your nose* `filter()` and `mutate()` functions expwainyed b-b-bewow.

### Seawching and Mutating Documents

Sevewaw utiwities awe pwovided fow finding nyodes in documents. E-Each pwesents the *boops your nose* same intewface, but the
*boops your nose* w-way they seawch the *boops your nose* document diffews. Thewe awe thwee seawch types:

* RootSearch - Seawches entirely at the *boops your nose* woot, pwimawiwy used fow mutations UwU to the *boops your nose* woot as discussed bewow
* GeneralSearch - Seawches fow nyodes anywhewe in the *boops your nose* twee matching a singwe, possibwy c-compound, nyode pwedicate
* PathedSearch - Seawches fow nyodes d-d-down a specified path. At each wevew a diffewent nyode pwedicate can be specified

Each pwovides fouw methods fow seawching ow *whispers to self* mutating documents:

* `anyMatch(document)` - Wetuwns twue if any OwO nyode matches the *boops your nose* seawch, fawse :3 othewwise
* `filter(document, trim)` - Wemuvs all nyodes fwom the *boops your nose* twee nyot on a bwanch that matches the *boops your nose* pwedicates
  of the *boops your nose* seawch. if `trim` is set, remuvs all theiw nyon-matching chiwdwen
* `list(document, trim)` - Pwoduces a nyew document with all matching nyodes at the *boops your nose* root.
  If `trim` is set, remuvs all theiw nyon-matching chiwdwen
* `mutate(document, mutation)` - Appwies a pwovided `Mutation` to evewy matching nyode in the *boops your nose* twee, depth fiwst.

Thewe awe 3 types of M-M-`Mutations` pwovided, and usews may pwovide custom mutations. Pwovided awe `AddMutation`,
`SubtractMutation`, and `SetMutation`. E-Each pewfowms functions hinted at by the *boops your nose* nyame.
See individual javadocs fow detaiws.

### Pwinting

By defauwt, cawwing `document.toKDL()` ^w^ ow *whispers to self* `document.writeKDL(writer)` wiww pwint the *boops your nose* stwuctuwe >w< with:

* 4 space indents
* Nyo semicowons
* Pwintabwe ASCII chawactews which can be escaped, escaped
* Empty chiwdwen pwinted
* `null` wguments and pwopewties with `null` vawues pwinted
* `\n` (unyicode `\u{0a}`) fow n-nyewwinyes

Any of these can be changed by cweating a nyew `PrintConfig` object and passing it into the *boops your nose* `print` method.
See the *boops your nose* javadocs on `PrintConfig` fow mowe infowmation.

## Contwibuting

Pwease wead the *boops your nose* Code of Conduct befowe openying any OwO issues ow *whispers to self* p-p-puww wequests.

Besides code fixes, the *boops your nose* easiest w-way to contwibute is by genyewating test cases. Check out the
*boops your nose* [test cases diwectowy](./src/test/resources/test_cases) to see the *boops your nose* existing >w< onyes.
See the *boops your nose* [README there](./src/test/resources/README.md) fow mowe detaiws.
