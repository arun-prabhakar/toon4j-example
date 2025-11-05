package im.arun.toon4j.example;

import im.arun.toon4j.DecodeOptions;
import im.arun.toon4j.Toon;

import java.util.List;
import java.util.Map;

/**
 * Comprehensive examples of decoding TOON format to Java objects.
 * Demonstrates various use cases and features of TOON4J decoder.
 */
public class DecoderExample {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          TOON4J Decoder Examples                              ║");
        System.out.println("║  Token-Oriented Object Notation for LLM Prompts               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        example1_SimplePrimitives();
        example2_SimpleObject();
        example3_NestedObjects();
        example4_PrimitiveArrays();
        example5_TabularArrays();
        example6_ComplexStructure();
        example7_CustomOptions();
        example8_ErrorHandling();
        example9_RoundTrip();
    }

    /**
     * Example 1: Decoding simple primitive values
     */
    private static void example1_SimplePrimitives() {
        printSectionHeader("Example 1: Simple Primitives");

        System.out.println("Integer:");
        Object intValue = Toon.decode("42");
        System.out.println("  Type: " + intValue.getClass().getSimpleName());
        System.out.println("  Value: " + intValue);
        System.out.println();

        System.out.println("String:");
        Object strValue = Toon.decode("hello");
        System.out.println("  Type: " + strValue.getClass().getSimpleName());
        System.out.println("  Value: " + strValue);
        System.out.println();

        System.out.println("Quoted String:");
        Object quotedStr = Toon.decode("\"hello, world\"");
        System.out.println("  Type: " + quotedStr.getClass().getSimpleName());
        System.out.println("  Value: " + quotedStr);
        System.out.println();

        System.out.println("Boolean:");
        Object boolValue = Toon.decode("true");
        System.out.println("  Type: " + boolValue.getClass().getSimpleName());
        System.out.println("  Value: " + boolValue);
        System.out.println();

        System.out.println("Null:");
        Object nullValue = Toon.decode("null");
        System.out.println("  Type: " + (nullValue == null ? "null" : nullValue.getClass().getSimpleName()));
        System.out.println("  Value: " + nullValue);
        System.out.println();

        System.out.println("Double:");
        Object doubleValue = Toon.decode("3.14159");
        System.out.println("  Type: " + doubleValue.getClass().getSimpleName());
        System.out.println("  Value: " + doubleValue);
        System.out.println();
    }

    /**
     * Example 2: Decoding a simple object
     */
    private static void example2_SimpleObject() {
        printSectionHeader("Example 2: Simple Object");

        String toon = """
            id: 123
            name: Ada Lovelace
            email: ada@example.com
            active: true
            """.trim();

        System.out.println("TOON Input:");
        System.out.println(toon);
        System.out.println();

        Object decoded = Toon.decode(toon);
        System.out.println("Decoded Type: " + decoded.getClass().getSimpleName());

        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) decoded;

        System.out.println("Fields:");
        System.out.println("  id: " + user.get("id") + " (" + user.get("id").getClass().getSimpleName() + ")");
        System.out.println("  name: " + user.get("name"));
        System.out.println("  email: " + user.get("email"));
        System.out.println("  active: " + user.get("active"));
        System.out.println();
    }

    /**
     * Example 3: Decoding nested objects
     */
    private static void example3_NestedObjects() {
        printSectionHeader("Example 3: Nested Objects");

        String toon = """
            id: 456
            name: Charles Babbage
            address:
              street: 10 Downing Street
              city: London
              country: UK
              zip: SW1A 2AA
            """.trim();

        System.out.println("TOON Input:");
        System.out.println(toon);
        System.out.println();

        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) Toon.decode(toon);

        System.out.println("User:");
        System.out.println("  id: " + user.get("id"));
        System.out.println("  name: " + user.get("name"));

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) user.get("address");
        System.out.println("  address:");
        System.out.println("    street: " + address.get("street"));
        System.out.println("    city: " + address.get("city"));
        System.out.println("    country: " + address.get("country"));
        System.out.println("    zip: " + address.get("zip"));
        System.out.println();
    }

    /**
     * Example 4: Decoding primitive arrays
     */
    private static void example4_PrimitiveArrays() {
        printSectionHeader("Example 4: Primitive Arrays");

        String toon = """
            tags[4]: java,toon,llm,encoding
            scores[3]: 95,87,92
            flags[3]: true,false,true
            """.trim();

        System.out.println("TOON Input:");
        System.out.println(toon);
        System.out.println();

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) Toon.decode(toon);

        @SuppressWarnings("unchecked")
        List<Object> tags = (List<Object>) data.get("tags");
        System.out.println("tags: " + tags);
        System.out.println("  Size: " + tags.size());
        System.out.println("  Items: " + tags);
        System.out.println();

        @SuppressWarnings("unchecked")
        List<Object> scores = (List<Object>) data.get("scores");
        System.out.println("scores: " + scores);
        System.out.println();

        @SuppressWarnings("unchecked")
        List<Object> flags = (List<Object>) data.get("flags");
        System.out.println("flags: " + flags);
        System.out.println();
    }

    /**
     * Example 5: Decoding tabular arrays
     */
    private static void example5_TabularArrays() {
        printSectionHeader("Example 5: Tabular Arrays");

        String toon = """
            users[3]{id,name,role,score}:
              1,Alice,admin,95.5
              2,Bob,user,87.3
              3,Charlie,moderator,92.1
            """.trim();

        System.out.println("TOON Input:");
        System.out.println(toon);
        System.out.println();

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) Toon.decode(toon);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> users = (List<Map<String, Object>>) data.get("users");

        System.out.println("Decoded " + users.size() + " users:");
        for (int i = 0; i < users.size(); i++) {
            Map<String, Object> user = users.get(i);
            System.out.printf("  User %d: id=%s, name=%s, role=%s, score=%s%n",
                    i + 1,
                    user.get("id"),
                    user.get("name"),
                    user.get("role"),
                    user.get("score"));
        }
        System.out.println();
    }

    /**
     * Example 6: Decoding complex structure
     */
    private static void example6_ComplexStructure() {
        printSectionHeader("Example 6: Complex Structure");

        String toon = """
            id: proj-001
            name: TOON4J
            version: 1.0.0
            tags[3]: java,serialization,llm
            author:
              name: Arun Prabhakar
              email: arun@example.com
              role: Lead Developer
            dependencies[2]{name,version,scope}:
              JUnit,5.10.0,test
              JMH,1.37,test
            stats:
              stars: 150
              forks: 23
              contributors: 5
            """.trim();

        System.out.println("TOON Input:");
        System.out.println(toon);
        System.out.println();

        @SuppressWarnings("unchecked")
        Map<String, Object> project = (Map<String, Object>) Toon.decode(toon);

        System.out.println("Project:");
        System.out.println("  id: " + project.get("id"));
        System.out.println("  name: " + project.get("name"));
        System.out.println("  version: " + project.get("version"));

        @SuppressWarnings("unchecked")
        List<Object> tags = (List<Object>) project.get("tags");
        System.out.println("  tags: " + tags);

        @SuppressWarnings("unchecked")
        Map<String, Object> author = (Map<String, Object>) project.get("author");
        System.out.println("  author: " + author.get("name") + " <" + author.get("email") + ">");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> deps = (List<Map<String, Object>>) project.get("dependencies");
        System.out.println("  dependencies: " + deps.size() + " items");

        @SuppressWarnings("unchecked")
        Map<String, Object> stats = (Map<String, Object>) project.get("stats");
        System.out.println("  stats: " + stats.get("stars") + " stars, " +
                stats.get("forks") + " forks, " +
                stats.get("contributors") + " contributors");
        System.out.println();
    }

    /**
     * Example 7: Using custom decode options
     */
    private static void example7_CustomOptions() {
        printSectionHeader("Example 7: Custom Decode Options");

        // 4-space indentation
        String toon = """
            user:
                name: Ada
                age: 25
            """.trim();

        System.out.println("TOON Input (4-space indent):");
        System.out.println(toon);
        System.out.println();

        DecodeOptions options = DecodeOptions.lenient(4);
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) Toon.decode(toon, options);

        System.out.println("Decoded:");
        System.out.println("  " + data);
        System.out.println();
    }

    /**
     * Example 8: Error handling
     */
    private static void example8_ErrorHandling() {
        printSectionHeader("Example 8: Error Handling");

        String[] invalidInputs = {
                "",                     // Empty input
                "   ",                  // Whitespace only
        };

        for (String input : invalidInputs) {
            try {
                System.out.println("Attempting to decode: \"" + input + "\"");
                Toon.decode(input);
                System.out.println("  Success");
            } catch (Exception e) {
                System.out.println("  Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
            System.out.println();
        }
    }

    /**
     * Example 9: Round-trip encoding and decoding
     */
    private static void example9_RoundTrip() {
        printSectionHeader("Example 9: Round-Trip Test");

        String originalToon = """
            status: success
            timestamp: 2025-01-04T10:30:00Z
            data[3]{id,name,score}:
              1,Alice,95.5
              2,Bob,87.3
              3,Charlie,92.1
            warnings[2]: Rate limit warning,API deprecation notice
            """.trim();

        System.out.println("Original TOON:");
        System.out.println(originalToon);
        System.out.println();

        // Decode
        Object decoded = Toon.decode(originalToon);
        System.out.println("Decoded to Java object");
        System.out.println();

        // Re-encode
        String reEncoded = Toon.encode(decoded);
        System.out.println("Re-encoded TOON:");
        System.out.println(reEncoded);
        System.out.println();

        // Verify
        Object reDecoded = Toon.decode(reEncoded);
        boolean matches = decoded.toString().equals(reDecoded.toString());
        System.out.println("Round-trip successful: " + matches);
        System.out.println();
    }

    // Helper method
    private static void printSectionHeader(String title) {
        System.out.println("═".repeat(65));
        System.out.println(title);
        System.out.println("═".repeat(65));
    }
}
