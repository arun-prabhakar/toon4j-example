package im.arun.toon4j.example;

import im.arun.toon4j.EncodeOptions;
import im.arun.toon4j.Toon;
import im.arun.toon4j.streaming.ToonStreamEncoder;
import im.arun.toon4j.validation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates advanced features including schema validation, streaming, and framework integrations.
 */
public class AdvancedFeaturesExample {

    public static void main(String[] args) throws Exception {
        System.out.println("=== TOON4J Advanced Features Examples ===\n");

        schemaValidationExample();
        streamingEncoderExample();
        nestedSchemaExample();
        constraintsExample();
        generateSchemaFromClassExample();
    }

    /**
     * Example 1: Basic Schema Validation
     */
    private static void schemaValidationExample() {
        System.out.println("1. Schema Validation Example");
        System.out.println("----------------------------");

        // Define schema
        ToonSchema schema = ToonSchema.builder()
                .field("id", SchemaType.INTEGER, true)
                .field("name", SchemaType.STRING, true)
                .field("email", SchemaType.STRING, true)
                .field("age", SchemaType.INTEGER, false)
                .build();

        // Valid data
        Map<String, Object> validData = Map.of(
                "id", 123,
                "name", "Alice",
                "email", "alice@example.com",
                "age", 30
        );

        ValidationResult validResult = schema.validate(validData);
        System.out.println("Valid data: " + validResult.isValid());

        // Invalid data (missing required field)
        Map<String, Object> invalidData = Map.of(
                "id", 123,
                "name", "Bob"
                // missing email!
        );

        ValidationResult invalidResult = schema.validate(invalidData);
        System.out.println("Invalid data: " + invalidResult.isValid());
        if (!invalidResult.isValid()) {
            System.out.println("Errors:");
            for (ValidationError error : invalidResult.getErrors()) {
                System.out.println("  - " + error);
            }
        }

        // Validate TOON string directly
        String toonString = """
                id: 456
                name: Charlie
                email: charlie@example.com
                age: 25
                """;

        ValidationResult toonResult = schema.validateToon(toonString);
        System.out.println("TOON string validation: " + toonResult.isValid());

        System.out.println();
    }

    /**
     * Example 2: Streaming Encoder
     */
    private static void streamingEncoderExample() throws Exception {
        System.out.println("2. Streaming Encoder Example");
        System.out.println("----------------------------");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (ToonStreamEncoder encoder = new ToonStreamEncoder(outputStream, EncodeOptions.DEFAULT)) {
            encoder.beginObject();

            // Write simple fields
            encoder.writeFieldValue("title", "User Report");
            encoder.writeFieldValue("timestamp", "2025-01-01T00:00:00Z");

            // Write tabular array
            encoder.writeField("users");
            encoder.beginTabularArray(3, "id", "name", "department");

            encoder.writeTabularRow(1, "Alice", "Engineering");
            encoder.writeTabularRow(2, "Bob", "Marketing");
            encoder.writeTabularRow(3, "Charlie", "Sales");

            encoder.endArray();
            encoder.endObject();
        }

        String result = outputStream.toString();
        System.out.println("Streamed TOON output:");
        System.out.println(result);
        System.out.println();
    }

    /**
     * Example 3: Nested Schema Validation
     */
    private static void nestedSchemaExample() {
        System.out.println("3. Nested Schema Example");
        System.out.println("------------------------");

        // Define address schema
        ToonSchema addressSchema = ToonSchema.builder()
                .field("street", SchemaType.STRING, true)
                .field("city", SchemaType.STRING, true)
                .field("zipCode", SchemaType.STRING, false)
                .build();

        // Define order item schema
        ToonSchema itemSchema = ToonSchema.builder()
                .field("id", SchemaType.INTEGER, true)
                .field("name", SchemaType.STRING, true)
                .field("quantity", SchemaType.INTEGER, true)
                .field("price", SchemaType.DOUBLE, true)
                .build();

        // Define main order schema
        ToonSchema orderSchema = ToonSchema.builder()
                .field("orderId", SchemaType.STRING, true)
                .field("customerName", SchemaType.STRING, true)
                .objectField("shippingAddress", true, addressSchema)
                .arrayField("items", true, itemSchema)
                .build();

        // Create sample order
        Map<String, Object> order = Map.of(
                "orderId", "ORD-001",
                "customerName", "Alice Johnson",
                "shippingAddress", Map.of(
                        "street", "123 Main St",
                        "city", "Boston",
                        "zipCode", "02101"
                ),
                "items", List.of(
                        Map.of("id", 1, "name", "Widget", "quantity", 2, "price", 19.99),
                        Map.of("id", 2, "name", "Gadget", "quantity", 1, "price", 49.99)
                )
        );

        ValidationResult result = orderSchema.validate(order);
        System.out.println("Order validation: " + result.isValid());

        if (result.isValid()) {
            String toon = Toon.encode(order);
            System.out.println("Order in TOON format:");
            System.out.println(toon);
        }

        System.out.println();
    }

    /**
     * Example 4: Custom Constraints
     */
    private static void constraintsExample() {
        System.out.println("4. Custom Constraints Example");
        System.out.println("-----------------------------");

        ToonSchema schema = ToonSchema.builder()
                .field(FieldSchema.builder("age")
                        .type(SchemaType.INTEGER)
                        .required(true)
                        .constraint(value -> ((Integer) value) >= 18)
                        .constraint(value -> ((Integer) value) <= 120)
                        .build())
                .field(FieldSchema.builder("email")
                        .type(SchemaType.STRING)
                        .required(true)
                        .constraint(value -> ((String) value).contains("@"))
                        .constraint(value -> ((String) value).length() >= 5)
                        .build())
                .field(FieldSchema.builder("score")
                        .type(SchemaType.DOUBLE)
                        .required(true)
                        .constraint(value -> ((Number) value).doubleValue() >= 0.0)
                        .constraint(value -> ((Number) value).doubleValue() <= 100.0)
                        .build())
                .build();

        // Valid data
        Map<String, Object> validData = Map.of(
                "age", 25,
                "email", "user@example.com",
                "score", 85.5
        );

        ValidationResult validResult = schema.validate(validData);
        System.out.println("Valid data with constraints: " + validResult.isValid());

        // Invalid age (too young)
        Map<String, Object> invalidAge = Map.of(
                "age", 15,
                "email", "user@example.com",
                "score", 85.5
        );

        ValidationResult ageResult = schema.validate(invalidAge);
        System.out.println("Invalid age: " + ageResult.isValid());
        if (!ageResult.isValid()) {
            System.out.println("  Error: Age constraint failed (must be >= 18)");
        }

        // Invalid email (no @)
        Map<String, Object> invalidEmail = Map.of(
                "age", 25,
                "email", "notanemail",
                "score", 85.5
        );

        ValidationResult emailResult = schema.validate(invalidEmail);
        System.out.println("Invalid email: " + emailResult.isValid());
        if (!emailResult.isValid()) {
            System.out.println("  Error: Email constraint failed (must contain @)");
        }

        System.out.println();
    }

    /**
     * Example 5: Generate Schema from Java Class
     */
    private static void generateSchemaFromClassExample() {
        System.out.println("5. Generate Schema from Class Example");
        System.out.println("-------------------------------------");

        class Employee {
            public int id;
            public String name;
            public String department;
            public double salary;
        }

        // Generate schema automatically
        ToonSchema schema = ToonSchema.fromClass(Employee.class);

        // Validate data against generated schema
        Map<String, Object> employeeData = Map.of(
                "id", 101,
                "name", "John Doe",
                "department", "Engineering",
                "salary", 95000.0
        );

        ValidationResult result = schema.validate(employeeData);
        System.out.println("Employee data validation: " + result.isValid());

        if (result.isValid()) {
            System.out.println("Schema fields detected:");
            for (String fieldName : schema.getFields().keySet()) {
                FieldSchema field = schema.getFields().get(fieldName);
                System.out.println("  - " + fieldName + ": " + field.getType());
            }
        }

        System.out.println();
    }
}
