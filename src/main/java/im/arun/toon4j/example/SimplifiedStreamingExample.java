package im.arun.toon4j.example;

import im.arun.toon4j.Toon;
import im.arun.toon4j.streaming.StreamDecoder;
import im.arun.toon4j.streaming.StreamEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates the simplified streaming API with one-liners.
 */
public class SimplifiedStreamingExample {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Simplified Streaming API Examples ===\n");

        oneLineStreamEncode();
        oneLineStreamDecode();
        streamWithProcessing();
        fileStreamingExample();
    }

    /**
     * Example 1: One-line stream encode
     */
    private static void oneLineStreamEncode() throws Exception {
        System.out.println("1. One-Line Stream Encode");
        System.out.println("-------------------------");

        List<Map<String, Object>> users = List.of(
                Map.of("id", 1, "name", "Alice", "email", "alice@example.com"),
                Map.of("id", 2, "name", "Bob", "email", "bob@example.com"),
                Map.of("id", 3, "name", "Charlie", "email", "charlie@example.com")
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // ONE LINE! Stream encode to output
        Toon.streamEncode(output, Map.of("users", users));

        System.out.println("Encoded TOON:");
        System.out.println(output.toString());
        System.out.println();
    }

    /**
     * Example 2: One-line stream decode
     */
    private static void oneLineStreamDecode() throws Exception {
        System.out.println("2. One-Line Stream Decode");
        System.out.println("-------------------------");

        String toonData = """
                users[3]{id,name,email}:
                  1,Alice,alice@example.com
                  2,Bob,bob@example.com
                  3,Charlie,charlie@example.com
                """;

        ByteArrayInputStream input = new ByteArrayInputStream(toonData.getBytes());

        // ONE LINE! Stream decode and process
        Toon.streamDecode(input).forEach(item -> {
            System.out.println("Received: " + item);
        });

        System.out.println();
    }

    /**
     * Example 3: Stream with processing
     */
    private static void streamWithProcessing() throws Exception {
        System.out.println("3. Stream with Processing");
        System.out.println("-------------------------");

        // Create sample data
        List<Map<String, Object>> products = List.of(
                Map.of("id", 1, "name", "Widget", "price", 19.99),
                Map.of("id", 2, "name", "Gadget", "price", 29.99),
                Map.of("id", 3, "name", "Doohickey", "price", 39.99)
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Encode
        try (StreamEncoder encoder = StreamEncoder.create(output)) {
            encoder.writeArray("products", products);
        }

        System.out.println("Encoded products:");
        System.out.println(output.toString());

        // Decode and process
        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

        System.out.println("\nProcessing decoded items:");
        StreamDecoder.fromInputStream(input).forEach(item -> {
            if (item instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) item;
                System.out.println("  Product: " + map.get("name") + " - $" + map.get("price"));
            }
        });

        System.out.println();
    }

    /**
     * Example 4: File streaming (memory efficient)
     */
    private static void fileStreamingExample() throws Exception {
        System.out.println("4. File Streaming Example");
        System.out.println("-------------------------");

        // Simulate large dataset
        List<Map<String, Object>> largeDataset = new java.util.ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            largeDataset.add(Map.of(
                    "id", i,
                    "name", "User" + i,
                    "email", "user" + i + "@example.com"
            ));
        }

        String filename = "large_users.toon";

        // ONE LINE! Stream large dataset to file
        try (FileOutputStream fileOutput = new FileOutputStream(filename)) {
            Toon.streamEncode(fileOutput, Map.of("users", largeDataset));
        }

        System.out.println("Streamed 1000 users to file: " + filename);

        // Read back and count (without loading all into memory)
        try (java.io.FileInputStream fileInput = new java.io.FileInputStream(filename)) {
            long count = Toon.streamDecode(fileInput).count();
            System.out.println("Decoded " + count + " items from file");
        }

        // Clean up
        new java.io.File(filename).delete();

        System.out.println();
    }

    /**
     * BONUS: Real-world HTTP streaming example (commented out)
     */
    private static void httpStreamingExample() throws Exception {
        /*
        // In a Spring Controller:

        @GetMapping(value = "/users/export", produces = "text/toon")
        public void exportUsers(HttpServletResponse response) throws IOException {
            // ONE LINE! Stream millions of users
            Toon.streamEncode(
                response.getOutputStream(),
                Map.of("users", userRepository.findAll())
            );
        }

        // On the client:
        Toon.streamDecode(httpResponse.getInputStream())
            .forEach(user -> processUser(user));
        */
    }
}
