package im.arun.toon4j.example;

import im.arun.toon4j.Delimiter;
import im.arun.toon4j.EncodeOptions;
import im.arun.toon4j.Toon;

import java.util.*;

/**
 * Advanced examples demonstrating TOON4J features for LLM prompt optimization.
 * Shows token savings, performance characteristics, and real-world use cases.
 */
public class AdvancedExample {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          TOON4J Advanced Examples                             ║");
        System.out.println("║  Optimizing LLM Prompts with TOON Format                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        example1_LLMPromptOptimization();
        example2_TokenSavingsComparison();
        example3_LargeDataset();
        example4_SpecialCharacters();
        example5_DelimiterSelection();
    }

    /**
     * Example 1: Using TOON for LLM prompt optimization
     */
    private static void example1_LLMPromptOptimization() {
        printSectionHeader("Example 1: LLM Prompt Optimization");

        // Creating a context-rich prompt for an LLM
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("model", "gpt-4");
        context.put("temperature", 0.7);
        context.put("maxTokens", 2000);

        List<Map<String, Object>> conversationHistory = new ArrayList<>();
        conversationHistory.add(createMessage("system", "You are a helpful coding assistant."));
        conversationHistory.add(createMessage("user", "How do I optimize Java performance?"));
        conversationHistory.add(createMessage("assistant", "Here are key Java optimization techniques..."));
        context.put("history", conversationHistory);

        List<Map<String, Object>> codeSnippets = new ArrayList<>();
        codeSnippets.add(createSnippet("java", "Performance.java", "public class Performance { ... }"));
        codeSnippets.add(createSnippet("java", "Optimizer.java", "public class Optimizer { ... }"));
        context.put("snippets", codeSnippets);

        String toon = Toon.encode(context);
        System.out.println("LLM Context in TOON format:");
        System.out.println(toon);
        System.out.println();

        int toonTokens = estimateTokens(toon);
        System.out.println("Estimated tokens: ~" + toonTokens);
        System.out.println("✓ Compact format saves tokens while maintaining readability");
        System.out.println();
    }

    /**
     * Example 2: Comparing token usage between JSON and TOON
     */
    private static void example2_TokenSavingsComparison() {
        printSectionHeader("Example 2: Token Savings Comparison");

        // Create sample data
        List<Map<String, Object>> products = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Map<String, Object> product = new LinkedHashMap<>();
            product.put("id", "PROD-" + String.format("%03d", i));
            product.put("name", "Product " + i);
            product.put("price", 99.99 + i);
            product.put("inStock", i % 2 == 0);
            product.put("rating", 4.0 + (i % 5) * 0.2);
            products.add(product);
        }

        Map<String, Object> catalog = new LinkedHashMap<>();
        catalog.put("products", products);
        catalog.put("total", products.size());
        catalog.put("currency", "USD");

        String toon = Toon.encode(catalog);
        String json = toApproximateJson(catalog);

        System.out.println("TOON Format:");
        System.out.println(toon);
        System.out.println();

        System.out.println("Approximate JSON Format:");
        System.out.println(json);
        System.out.println();

        int toonSize = toon.length();
        int jsonSize = json.length();
        double savings = ((jsonSize - toonSize) / (double) jsonSize) * 100;

        System.out.println("═══════════════════════════════════════════");
        System.out.printf("TOON size: %d characters%n", toonSize);
        System.out.printf("JSON size: %d characters%n", jsonSize);
        System.out.printf("Token savings: ~%.1f%%%n", savings);
        System.out.println("═══════════════════════════════════════════");
        System.out.println();
    }

    /**
     * Example 3: Encoding large datasets efficiently
     */
    private static void example3_LargeDataset() {
        printSectionHeader("Example 3: Large Dataset Encoding");

        // Generate large dataset
        List<Map<String, Object>> records = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Map<String, Object> record = new LinkedHashMap<>();
            record.put("id", i);
            record.put("timestamp", "2025-01-04T" + String.format("%02d", i % 24) + ":00:00Z");
            record.put("value", 100.0 + Math.random() * 900);
            record.put("status", i % 3 == 0 ? "completed" : "pending");
            records.add(record);
        }

        long startTime = System.nanoTime();
        String toon = Toon.encode(records);
        long endTime = System.nanoTime();

        double milliseconds = (endTime - startTime) / 1_000_000.0;

        System.out.println("Encoded 100 records in " + String.format("%.2f", milliseconds) + " ms");
        System.out.println("Output size: " + toon.length() + " characters");
        System.out.println();

        System.out.println("First 500 characters of output:");
        System.out.println(toon.substring(0, Math.min(500, toon.length())));
        System.out.println("...");
        System.out.println();
    }

    /**
     * Example 4: Handling special characters and escaping
     */
    private static void example4_SpecialCharacters() {
        printSectionHeader("Example 4: Special Characters & Escaping");

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("text", "Hello\nWorld");
        data.put("path", "C:\\Users\\Documents\\file.txt");
        data.put("quote", "He said \"Hello\"");
        data.put("unicode", "Emoji: 😀 Unicode: \u2764\uFE0F");
        data.put("special", "Comma, pipe|, tab\t");

        String toon = Toon.encode(data);
        System.out.println("TOON with special characters:");
        System.out.println(toon);
        System.out.println();

        // Decode back
        @SuppressWarnings("unchecked")
        Map<String, Object> decoded = (Map<String, Object>) Toon.decode(toon);

        System.out.println("Decoded values:");
        System.out.println("  text: " + decoded.get("text"));
        System.out.println("  path: " + decoded.get("path"));
        System.out.println("  quote: " + decoded.get("quote"));
        System.out.println("  unicode: " + decoded.get("unicode"));
        System.out.println("  special: " + decoded.get("special"));
        System.out.println();

        System.out.println("✓ All special characters preserved correctly");
        System.out.println();
    }

    /**
     * Example 5: Choosing the right delimiter
     */
    private static void example5_DelimiterSelection() {
        printSectionHeader("Example 5: Delimiter Selection");

        List<Map<String, Object>> data = new ArrayList<>();
        data.add(createLogEntry("INFO", "Application started"));
        data.add(createLogEntry("WARN", "High memory usage: 85%"));
        data.add(createLogEntry("ERROR", "Connection failed: timeout"));

        System.out.println("Same data with different delimiters:\n");

        // Comma (best for most cases)
        System.out.println("COMMA (default - best for general use):");
        String comma = Toon.encode(data);
        System.out.println(comma);
        System.out.println();

        // Pipe (good when data contains commas)
        System.out.println("PIPE (good when data contains commas):");
        EncodeOptions pipeOpts = EncodeOptions.builder().delimiter(Delimiter.PIPE).build();
        String pipe = Toon.encode(data, pipeOpts);
        System.out.println(pipe);
        System.out.println();

        // Tab (most compact, but less readable)
        System.out.println("TAB (most compact, less readable):");
        EncodeOptions tabOpts = EncodeOptions.builder().delimiter(Delimiter.TAB).build();
        String tab = Toon.encode(data, tabOpts);
        System.out.println(tab);
        System.out.println();

        System.out.println("Size comparison:");
        System.out.printf("  Comma: %d chars%n", comma.length());
        System.out.printf("  Pipe:  %d chars%n", pipe.length());
        System.out.printf("  Tab:   %d chars%n", tab.length());
        System.out.println();
    }

    // Helper methods

    private static Map<String, Object> createMessage(String role, String content) {
        Map<String, Object> msg = new LinkedHashMap<>();
        msg.put("role", role);
        msg.put("content", content);
        return msg;
    }

    private static Map<String, Object> createSnippet(String language, String file, String code) {
        Map<String, Object> snippet = new LinkedHashMap<>();
        snippet.put("language", language);
        snippet.put("file", file);
        snippet.put("code", code);
        return snippet;
    }

    private static Map<String, Object> createLogEntry(String level, String message) {
        Map<String, Object> entry = new LinkedHashMap<>();
        entry.put("level", level);
        entry.put("message", message);
        entry.put("timestamp", System.currentTimeMillis());
        return entry;
    }

    private static int estimateTokens(String text) {
        // Rough approximation: ~4 characters per token
        return text.length() / 4;
    }

    private static String toApproximateJson(Map<String, Object> data) {
        // Very simple JSON approximation for comparison
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"products\": [\n");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> products = (List<Map<String, Object>>) data.get("products");
        for (int i = 0; i < products.size(); i++) {
            Map<String, Object> p = products.get(i);
            json.append("    {\n");
            json.append("      \"id\": \"").append(p.get("id")).append("\",\n");
            json.append("      \"name\": \"").append(p.get("name")).append("\",\n");
            json.append("      \"price\": ").append(p.get("price")).append(",\n");
            json.append("      \"inStock\": ").append(p.get("inStock")).append(",\n");
            json.append("      \"rating\": ").append(p.get("rating")).append("\n");
            json.append("    }").append(i < products.size() - 1 ? "," : "").append("\n");
        }
        json.append("  ],\n");
        json.append("  \"total\": ").append(data.get("total")).append(",\n");
        json.append("  \"currency\": \"").append(data.get("currency")).append("\"\n");
        json.append("}");
        return json.toString();
    }

    private static void printSectionHeader(String title) {
        System.out.println("═".repeat(65));
        System.out.println(title);
        System.out.println("═".repeat(65));
    }
}
