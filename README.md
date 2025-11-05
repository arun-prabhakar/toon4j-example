# TOON4J Examples

Comprehensive examples demonstrating how to use TOON4J library for encoding and decoding Token-Oriented Object Notation (TOON) format.

## What is TOON?

TOON (Token-Oriented Object Notation) is a compact data serialization format designed for Large Language Model (LLM) prompts. It achieves **30-60% fewer tokens than JSON** while maintaining human readability.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- TOON4J library (automatically downloaded from Maven Central)

## Project Structure

```
toon4j-example/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
└── src/main/java/im/arun/toon4j/example/
    ├── EncoderExample.java          # Encoding examples
    ├── DecoderExample.java          # Decoding examples
    ├── AdvancedExample.java         # Advanced features
    └── PojoExample.java             # POJO serialization/deserialization
```

## Running the Examples

### 1. Build the project

```bash
cd toon4j-example
mvn clean compile
```

### 2. Run individual examples

**Encoder Example:**
```bash
mvn exec:java -Dexec.mainClass="im.arun.toon4j.example.EncoderExample"
```

**Decoder Example:**
```bash
mvn exec:java -Dexec.mainClass="im.arun.toon4j.example.DecoderExample"
```

**Advanced Example:**
```bash
mvn exec:java -Dexec.mainClass="im.arun.toon4j.example.AdvancedExample"
```

**POJO Example:**
```bash
mvn exec:java -Dexec.mainClass="im.arun.toon4j.example.PojoExample"
```

### 3. Or run all examples

```bash
java -cp target/toon4j-example-1.0.0.jar:~/.m2/repository/im/arun/toon4j/1.0.0/toon4j-1.0.0.jar im.arun.toon4j.example.EncoderExample
java -cp target/toon4j-example-1.0.0.jar:~/.m2/repository/im/arun/toon4j/1.0.0/toon4j-1.0.0.jar im.arun.toon4j.example.DecoderExample
java -cp target/toon4j-example-1.0.0.jar:~/.m2/repository/im/arun/toon4j/1.0.0/toon4j-1.0.0.jar im.arun.toon4j.example.AdvancedExample
java -cp target/toon4j-example-1.0.0.jar:~/.m2/repository/im/arun/toon4j/1.0.0/toon4j-1.0.0.jar im.arun.toon4j.example.PojoExample
```

## Example Overview

### EncoderExample.java

Demonstrates encoding Java objects to TOON format:

1. **Simple Primitives** - Encoding basic types (int, string, boolean, null)
2. **Simple Objects** - Encoding Maps to TOON objects
3. **Nested Objects** - Encoding nested data structures
4. **Primitive Arrays** - Inline array encoding
5. **Tabular Arrays** - Compact array-of-objects encoding
6. **Complex Structures** - Real-world nested data
7. **Custom Options** - Using EncodeOptions (indent, delimiters, etc.)
8. **Different Delimiters** - Comma, pipe, and tab delimiters
9. **Real-World Example** - API response encoding with token savings

### DecoderExample.java

Demonstrates decoding TOON format to Java objects:

1. **Simple Primitives** - Decoding basic values
2. **Simple Objects** - Decoding to Map
3. **Nested Objects** - Decoding nested structures
4. **Primitive Arrays** - Decoding inline arrays
5. **Tabular Arrays** - Decoding tabular format
6. **Complex Structures** - Decoding complex nested data
7. **Custom Options** - Using DecodeOptions
8. **Error Handling** - Handling invalid input
9. **Round-Trip** - Encode→Decode→Encode verification

### AdvancedExample.java

Demonstrates advanced features for LLM optimization:

1. **LLM Prompt Optimization** - Using TOON in LLM contexts
2. **Token Savings** - Comparing TOON vs JSON token usage
3. **Large Datasets** - Performance with 100+ records
4. **Special Characters** - Escaping and Unicode handling
5. **Delimiter Selection** - Choosing optimal delimiters

### PojoExample.java

Demonstrates automatic POJO serialization and deserialization:

1. **Simple POJO Round-Trip** - Encode and decode basic POJOs
2. **Nested POJOs** - Handling nested object structures
3. **POJOs with Collections** - Lists and Sets in POJOs
4. **List of POJOs** - Automatic List<Map> → List<POJO> conversion
5. **Java Records** - Support for Java 17+ records
6. **POJOs with Enums** - Enum serialization/deserialization
7. **Complex Real-World Example** - Complete project structure with token savings

## Quick Start Code

### Encoding

```java
import im.arun.toon4j.Toon;
import java.util.*;

// Create data
Map<String, Object> user = new LinkedHashMap<>();
user.put("id", 123);
user.put("name", "Ada");
user.put("tags", List.of("admin", "dev"));

// Encode to TOON
String toon = Toon.encode(user);
System.out.println(toon);

// Output:
// id: 123
// name: Ada
// tags[2]: admin,dev
```

### Decoding

```java
import im.arun.toon4j.Toon;

// TOON format
String toon = """
    id: 123
    name: Ada
    tags[2]: admin,dev
    """.trim();

// Decode to Java
Object decoded = Toon.decode(toon);
Map<String, Object> user = (Map<String, Object>) decoded;

System.out.println("Name: " + user.get("name"));
// Output: Name: Ada
```

### POJO Deserialization

```java
import im.arun.toon4j.Toon;

// Define your POJO
public class User {
    private int id;
    private String name;
    private String email;
    private boolean active;

    // Getters and setters...
}

// TOON format
String toon = """
    id: 123
    name: Ada
    email: ada@example.com
    active: true
    """.trim();

// Decode directly to POJO - no manual casting!
User user = Toon.decode(toon, User.class);

System.out.println("Name: " + user.getName());
System.out.println("ID: " + user.getId());
// Output: Name: Ada
// Output: ID: 123
```

**Supports:**
- JavaBeans (with setters), public fields, and Java Records
- Nested POJOs and collections with generics (`List<Employee>`)
- Automatic type conversion (Integer → Long, etc.)
- Enums, arrays, and complex nested structures

## Custom Options

### Encoding Options

```java
import im.arun.toon4j.*;

EncodeOptions options = EncodeOptions.builder()
    .indent(4)                          // 4-space indentation
    .delimiter(Delimiter.PIPE)          // Use pipe delimiter
    .lengthMarker(true)                 // Add # to array lengths
    .build();

String toon = Toon.encode(data, options);
```

### Decoding Options

```java
import im.arun.toon4j.DecodeOptions;

DecodeOptions options = DecodeOptions.lenient(4);  // 4-space indent
Object decoded = Toon.decode(toon, options);
```

## Key Features Demonstrated

✅ **Compact Format** - 30-60% fewer tokens than JSON
✅ **Tabular Arrays** - Efficient encoding of homogeneous data
✅ **Multiple Delimiters** - Comma, pipe, and tab support
✅ **Zero Dependencies** - Pure Java implementation
✅ **Type Preservation** - Automatic type detection
✅ **Unicode Support** - Full UTF-8 and escape sequences
✅ **High Performance** - Sub-5ms encoding for 256KB data

## Common Use Cases

### 1. LLM Prompt Context

```java
Map<String, Object> context = new LinkedHashMap<>();
context.put("model", "gpt-4");
context.put("temperature", 0.7);
context.put("history", conversationHistory);
context.put("codeSnippets", snippets);

String prompt = Toon.encode(context);
// Send to LLM - saves tokens compared to JSON!
```

### 2. Configuration Files

```java
Map<String, Object> config = new LinkedHashMap<>();
config.put("database", dbConfig);
config.put("servers", serverList);
config.put("features", featureFlags);

String configToon = Toon.encode(config);
Files.writeString(Path.of("config.toon"), configToon);
```

### 3. API Responses

```java
Map<String, Object> response = new LinkedHashMap<>();
response.put("status", "success");
response.put("data", results);
response.put("metadata", metadata);

return Toon.encode(response);
```

## Performance Tips

1. **Use tabular format** for arrays of similar objects
2. **Choose delimiters** based on your data (tab is most compact)
3. **Enable length markers** for strict parsing when needed
4. **Reuse EncodeOptions** instances for better performance

## Token Savings Example

**JSON (165 characters):**
```json
{"products":[{"id":"A1","name":"Laptop","price":999.99},{"id":"A2","name":"Mouse","price":29.99}],"total":2}
```

**TOON (98 characters - 40% savings!):**
```
products[2]{id,name,price}:
  A1,Laptop,999.99
  A2,Mouse,29.99
total: 2
```

## Documentation

- [TOON4J GitHub](https://github.com/arun-prabhakar/toon4j)
- [TOON Format Specification](https://github.com/arun-prabhakar/toon4j/blob/main/README.md)
- [API Documentation](https://javadoc.io/doc/im.arun/toon4j)

## License

This example project is provided under the MIT License, same as TOON4J.

## Support

For issues or questions:
- Open an issue on [GitHub](https://github.com/arun-prabhakar/toon4j/issues)
- Email: arunprabhakar.t@gmail.com

---

**Happy Encoding!** 🚀
