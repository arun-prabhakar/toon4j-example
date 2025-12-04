package im.arun.toon4j.example;

import im.arun.toon4j.Delimiter;
import im.arun.toon4j.EncodeOptions;
import im.arun.toon4j.Toon;
import im.arun.toon4j.KeyFolding;
import im.arun.toon4j.EncodeReplacer;

import java.util.*;

/**
 * Comprehensive examples of encoding data to TOON format.
 * Demonstrates various use cases and features of TOON4J encoder.
 */
public class EncoderExample {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          TOON4J Encoder Examples                              ║");
        System.out.println("║  Token-Oriented Object Notation for LLM Prompts               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        example1_SimplePrimitives();
        example2_SimpleObject();
        example3_NestedObjects();
        example4_PrimitiveArrays();
        example5_TabularArrays();
        example6_ComplexStructure();
        example7_CustomOptions();
        example8_DifferentDelimiters();
        example9_RealWorldExample();
        example10_StreamLines();
    }

    /**
     * Example 1: Encoding simple primitive values
     */
    private static void example1_SimplePrimitives() {
        printSectionHeader("Example 1: Simple Primitives");

        System.out.println("Integer:");
        System.out.println(Toon.encode(42));
        System.out.println();

        System.out.println("String:");
        System.out.println(Toon.encode("Hello, World!"));
        System.out.println();

        System.out.println("Boolean:");
        System.out.println(Toon.encode(true));
        System.out.println();

        System.out.println("Null:");
        System.out.println(Toon.encode(null));
        System.out.println();
    }

    /**
     * Example 2: Encoding a simple object
     */
    private static void example2_SimpleObject() {
        printSectionHeader("Example 2: Simple Object");

        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", 123);
        user.put("name", "Ada Lovelace");
        user.put("email", "ada@example.com");
        user.put("active", true);

        String encoded = Toon.encode(user);
        System.out.println(encoded);
        System.out.println();
    }

    /**
     * Example 3: Encoding nested objects
     */
    private static void example3_NestedObjects() {
        printSectionHeader("Example 3: Nested Objects");

        Map<String, Object> address = new LinkedHashMap<>();
        address.put("street", "10 Downing Street");
        address.put("city", "London");
        address.put("country", "UK");
        address.put("zip", "SW1A 2AA");

        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", 456);
        user.put("name", "Charles Babbage");
        user.put("address", address);

        String encoded = Toon.encode(user);
        System.out.println(encoded);
        System.out.println();
    }

    /**
     * Example 4: Encoding primitive arrays (inline format)
     */
    private static void example4_PrimitiveArrays() {
        printSectionHeader("Example 4: Primitive Arrays (Inline)");

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tags", List.of("java", "toon", "llm", "encoding"));
        data.put("scores", List.of(95, 87, 92, 88));
        data.put("flags", List.of(true, false, true));

        String encoded = Toon.encode(data);
        System.out.println(encoded);
        System.out.println();
    }

    /**
     * Example 5: Encoding tabular arrays (array of objects)
     */
    private static void example5_TabularArrays() {
        printSectionHeader("Example 5: Tabular Arrays");

        List<Map<String, Object>> users = new ArrayList<>();

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 1);
        user1.put("name", "Alice");
        user1.put("role", "admin");
        user1.put("score", 95.5);
        users.add(user1);

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("id", 2);
        user2.put("name", "Bob");
        user2.put("role", "user");
        user2.put("score", 87.3);
        users.add(user2);

        Map<String, Object> user3 = new LinkedHashMap<>();
        user3.put("id", 3);
        user3.put("name", "Charlie");
        user3.put("role", "moderator");
        user3.put("score", 92.1);
        users.add(user3);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("users", users);

        String encoded = Toon.encode(data);
        System.out.println(encoded);
        System.out.println();
    }

    /**
     * Example 6: Complex nested structure
     */
    private static void example6_ComplexStructure() {
        printSectionHeader("Example 6: Complex Nested Structure");

        Map<String, Object> project = new LinkedHashMap<>();
        project.put("id", "proj-001");
        project.put("name", "TOON4J");
        project.put("description", "High-performance TOON encoder/decoder for Java");
        project.put("version", "1.0.0");
        project.put("tags", List.of("java", "serialization", "llm"));

        Map<String, Object> author = new LinkedHashMap<>();
        author.put("name", "Arun Prabhakar");
        author.put("email", "arun@example.com");
        author.put("role", "Lead Developer");
        project.put("author", author);

        List<Map<String, Object>> dependencies = new ArrayList<>();
        dependencies.add(createDependency("JUnit", "5.10.0", "test"));
        dependencies.add(createDependency("JMH", "1.37", "test"));
        project.put("dependencies", dependencies);

        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("stars", 150);
        stats.put("forks", 23);
        stats.put("contributors", 5);
        project.put("stats", stats);

        String encoded = Toon.encode(project);
        System.out.println(encoded);
        System.out.println();
    }

    /**
     * Example 7: Using custom encode options
     */
    private static void example7_CustomOptions() {
        printSectionHeader("Example 7: Custom Encode Options");

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("name", "Example");

        Map<String, Object> nested = new LinkedHashMap<>();
        nested.put("value", 42);
        data.put("nested", nested);
        data.put("items", List.of("a", "b", "c"));

        // Default (2-space indent)
        System.out.println("Default (2-space indent):");
        System.out.println(Toon.encode(data));
        System.out.println();

        // 4-space indent
        System.out.println("4-space indent:");
        EncodeOptions fourSpaces = EncodeOptions.builder()
                .indent(4)
                .build();
        System.out.println(Toon.encode(data, fourSpaces));
        System.out.println();

        // Safe key folding for single-key chains
        System.out.println("With safe key folding:");
        EncodeOptions folding = EncodeOptions.builder()
                .keyFolding(KeyFolding.SAFE)
                .flattenDepth(3)
                .build();
        System.out.println(Toon.encode(data, folding));
        System.out.println();

        // Aggressive flatten (forces dotted keys for single-key wrappers)
        System.out.println("Aggressive flatten (flatten=true):");
        EncodeOptions flattenOptions = EncodeOptions.builder()
                .flatten(true)
                .flattenDepth(3)
                .build();
        Map<String, Object> nestedWrapper = new LinkedHashMap<>();
        nestedWrapper.put("meta", Map.of("info", Map.of("id", 123, "env", "prod")));
        System.out.println("Input: " + nestedWrapper);
        System.out.println("Output:");
        System.out.println(Toon.encode(nestedWrapper, flattenOptions));
        System.out.println();

        // Custom replacer to drop sensitive fields
        System.out.println("With replacer (hiding `secret`)");
        EncodeOptions withReplacer = EncodeOptions.builder()
                .replacer((key, value, path) -> "secret".equals(key) ? EncodeReplacer.OMIT : value)
                .build();
        Map<String, Object> withSecret = new LinkedHashMap<>(data);
        withSecret.put("secret", "top-secret");
        System.out.println(Toon.encode(withSecret, withReplacer));
        System.out.println();

        // Compact
        System.out.println("Compact:");
        System.out.println(Toon.encode(data, EncodeOptions.compact()));
        System.out.println();

        // Verbose
        System.out.println("Verbose:");
        System.out.println(Toon.encode(data, EncodeOptions.verbose()));
        System.out.println();
    }

    /**
     * Example 8: Different delimiters
     */
    private static void example8_DifferentDelimiters() {
        printSectionHeader("Example 8: Different Delimiters");

        List<Map<String, Object>> data = new ArrayList<>();
        data.add(createProduct("SKU-001", "Laptop", 999.99));
        data.add(createProduct("SKU-002", "Mouse", 29.99));
        data.add(createProduct("SKU-003", "Keyboard", 79.99));

        // Comma delimiter (default)
        System.out.println("Comma delimiter (default):");
        System.out.println(Toon.encode(data));
        System.out.println();

        // Pipe delimiter
        System.out.println("Pipe delimiter:");
        EncodeOptions pipeOptions = EncodeOptions.builder()
                .delimiter(Delimiter.PIPE)
                .build();
        System.out.println(Toon.encode(data, pipeOptions));
        System.out.println();

        // Tab delimiter
        System.out.println("Tab delimiter:");
        EncodeOptions tabOptions = EncodeOptions.builder()
                .delimiter(Delimiter.TAB)
                .build();
        System.out.println(Toon.encode(data, tabOptions));
        System.out.println();
    }

    /**
     * Example 10: Streaming lines with encodeLines
     */
    private static void example10_StreamLines() {
        printSectionHeader("Example 10: Streaming encodeLines");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("team", "Platform");
        payload.put("members", List.of(
                createUser(1, "Ada", "Engineering"),
                createUser(2, "Bob", "Product")
        ));

        for (String line : Toon.encodeLines(payload, EncodeOptions.builder().keyFolding(KeyFolding.SAFE).build())) {
            System.out.println(line);
        }
        System.out.println();
    }

    /**
     * Example 9: Real-world API response encoding
     */
    private static void example9_RealWorldExample() {
        printSectionHeader("Example 9: Real-World API Response");

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("timestamp", "2025-01-04T10:30:00Z");
        response.put("requestId", "req-abc-123");

        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("page", 1);
        metadata.put("pageSize", 10);
        metadata.put("totalRecords", 25);
        metadata.put("hasMore", true);
        response.put("metadata", metadata);

        List<Map<String, Object>> items = new ArrayList<>();
        items.add(createOrder("ORD-001", "Alice", 299.99, "completed"));
        items.add(createOrder("ORD-002", "Bob", 149.50, "pending"));
        items.add(createOrder("ORD-003", "Charlie", 599.00, "completed"));
        response.put("data", items);

        List<String> warnings = List.of(
                "Rate limit: 45/100 requests remaining",
                "API version 2.0 will be deprecated on 2025-12-31"
        );
        response.put("warnings", warnings);

        String encoded = Toon.encode(response);
        System.out.println(encoded);
        System.out.println();

        // Calculate token savings
        String json = toJson(response);
        int toonLength = encoded.length();
        int jsonLength = json.length();
        double savings = ((jsonLength - toonLength) / (double) jsonLength) * 100;

        System.out.println("═══════════════════════════════════════════");
        System.out.printf("TOON size: %d characters%n", toonLength);
        System.out.printf("JSON size: %d characters%n", jsonLength);
        System.out.printf("Savings: %.1f%%%n", savings);
        System.out.println("═══════════════════════════════════════════");
    }

    // Helper methods

    private static Map<String, Object> createDependency(String name, String version, String scope) {
        Map<String, Object> dep = new LinkedHashMap<>();
        dep.put("name", name);
        dep.put("version", version);
        dep.put("scope", scope);
        return dep;
    }

    private static Map<String, Object> createProduct(String sku, String name, double price) {
        Map<String, Object> product = new LinkedHashMap<>();
        product.put("sku", sku);
        product.put("name", name);
        product.put("price", price);
        return product;
    }

    private static Map<String, Object> createOrder(String id, String customer, double amount, String status) {
        Map<String, Object> order = new LinkedHashMap<>();
        order.put("orderId", id);
        order.put("customer", customer);
        order.put("amount", amount);
        order.put("status", status);
        return order;
    }

    private static Map<String, Object> createUser(int id, String name, String team) {
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", id);
        user.put("name", name);
        user.put("team", team);
        return user;
    }

    private static void printSectionHeader(String title) {
        System.out.println("═".repeat(65));
        System.out.println(title);
        System.out.println("═".repeat(65));
    }

    private static String toJson(Map<String, Object> data) {
        // Simple JSON approximation for size comparison
        // In real usage, you'd use Jackson or similar
        return data.toString().replace("=", ":");
    }
}
