package im.arun.toon4j.example;

import im.arun.toon4j.Toon;

import java.util.*;

/**
 * Comprehensive examples demonstrating POJO serialization and deserialization.
 * Shows automatic type conversion, nested objects, collections, records, and enums.
 */
public class PojoExample {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          TOON4J POJO Examples                                 ║");
        System.out.println("║  Automatic Serialization and Deserialization                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        example1_SimplePojoRoundTrip();
        example2_NestedPojo();
        example3_PojoWithCollections();
        example4_ListOfPojos();
        example5_JavaRecord();
        example6_PojoWithEnum();
        example7_ComplexRealWorld();
    }

    // ==================== POJOs ====================

    public static class User {
        private int id;
        private String name;
        private String email;
        private boolean active;

        public User() {}

        public User(int id, String name, String email, boolean active) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.active = active;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
    }

    public static class Address {
        private String street;
        private String city;
        private String zipCode;
        private String country;

        public Address() {}

        public Address(String street, String city, String zipCode, String country) {
            this.street = street;
            this.city = city;
            this.zipCode = zipCode;
            this.country = country;
        }

        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }

    public static class Employee {
        private int id;
        private String name;
        private String department;
        private Address address;

        public Employee() {}

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }
    }

    public static class Team {
        private String name;
        private List<String> members;
        private Set<String> tags;

        public Team() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public List<String> getMembers() { return members; }
        public void setMembers(List<String> members) { this.members = members; }

        public Set<String> getTags() { return tags; }
        public void setTags(Set<String> tags) { this.tags = tags; }
    }

    public static class Department {
        private String name;
        private String location;
        private List<Employee> employees;

        public Department() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public List<Employee> getEmployees() { return employees; }
        public void setEmployees(List<Employee> employees) { this.employees = employees; }
    }

    public record Person(String name, int age, String city, String occupation) {}

    public enum Role {
        ADMIN, USER, MANAGER, GUEST
    }

    public static class Account {
        private String username;
        private Role role;
        private List<String> permissions;

        public Account() {}

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }

        public List<String> getPermissions() { return permissions; }
        public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    }

    public static class Project {
        private String id;
        private String name;
        private String description;
        private List<String> tags;
        private Employee owner;
        private List<Employee> contributors;
        private Map<String, Object> metadata;

        public Project() {}

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }

        public Employee getOwner() { return owner; }
        public void setOwner(Employee owner) { this.owner = owner; }

        public List<Employee> getContributors() { return contributors; }
        public void setContributors(List<Employee> contributors) { this.contributors = contributors; }

        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    }

    // ==================== Examples ====================

    /**
     * Example 1: Simple POJO round-trip (encode → decode)
     */
    private static void example1_SimplePojoRoundTrip() {
        printSectionHeader("Example 1: Simple POJO Round-Trip");

        // Create a POJO
        User user = new User(123, "Alice", "alice@example.com", true);

        // Encode to TOON
        String toon = Toon.encode(user);
        System.out.println("Encoded TOON:");
        System.out.println(toon);
        System.out.println();

        // Decode back to POJO
        User decoded = Toon.decode(toon, User.class);

        System.out.println("Decoded User:");
        System.out.println("  ID: " + decoded.getId());
        System.out.println("  Name: " + decoded.getName());
        System.out.println("  Email: " + decoded.getEmail());
        System.out.println("  Active: " + decoded.isActive());
        System.out.println();

        System.out.println("✓ Round-trip successful!");
        System.out.println();
    }

    /**
     * Example 2: Nested POJO
     */
    private static void example2_NestedPojo() {
        printSectionHeader("Example 2: Nested POJO");

        // Create nested POJOs
        Employee employee = new Employee();
        employee.setId(456);
        employee.setName("Bob");
        employee.setDepartment("Engineering");

        Address address = new Address("123 Main St", "Boston", "02101", "USA");
        employee.setAddress(address);

        // Encode to TOON
        String toon = Toon.encode(employee);
        System.out.println("Encoded TOON:");
        System.out.println(toon);
        System.out.println();

        // Decode back to POJO
        Employee decoded = Toon.decode(toon, Employee.class);

        System.out.println("Decoded Employee:");
        System.out.println("  ID: " + decoded.getId());
        System.out.println("  Name: " + decoded.getName());
        System.out.println("  Department: " + decoded.getDepartment());
        System.out.println("  Address:");
        System.out.println("    Street: " + decoded.getAddress().getStreet());
        System.out.println("    City: " + decoded.getAddress().getCity());
        System.out.println("    Zip: " + decoded.getAddress().getZipCode());
        System.out.println("    Country: " + decoded.getAddress().getCountry());
        System.out.println();
    }

    /**
     * Example 3: POJO with collections
     */
    private static void example3_PojoWithCollections() {
        printSectionHeader("Example 3: POJO with Collections");

        // Create POJO with collections
        Team team = new Team();
        team.setName("Alpha Team");
        team.setMembers(List.of("Alice", "Bob", "Charlie"));
        team.setTags(new LinkedHashSet<>(List.of("agile", "remote", "fullstack")));

        // Encode to TOON
        String toon = Toon.encode(team);
        System.out.println("Encoded TOON:");
        System.out.println(toon);
        System.out.println();

        // Decode back to POJO
        Team decoded = Toon.decode(toon, Team.class);

        System.out.println("Decoded Team:");
        System.out.println("  Name: " + decoded.getName());
        System.out.println("  Members: " + decoded.getMembers());
        System.out.println("  Tags: " + decoded.getTags());
        System.out.println();
    }

    /**
     * Example 4: List of POJOs (automatic conversion)
     */
    private static void example4_ListOfPojos() {
        printSectionHeader("Example 4: List of POJOs");

        // Create department with list of employees
        Department dept = new Department();
        dept.setName("Engineering");
        dept.setLocation("San Francisco");

        List<Employee> employees = new ArrayList<>();

        Employee emp1 = new Employee();
        emp1.setId(1);
        emp1.setName("Alice");
        emp1.setDepartment("Engineering");
        employees.add(emp1);

        Employee emp2 = new Employee();
        emp2.setId(2);
        emp2.setName("Bob");
        emp2.setDepartment("Engineering");
        employees.add(emp2);

        Employee emp3 = new Employee();
        emp3.setId(3);
        emp3.setName("Charlie");
        emp3.setDepartment("Engineering");
        employees.add(emp3);

        dept.setEmployees(employees);

        // Encode to TOON
        String toon = Toon.encode(dept);
        System.out.println("Encoded TOON:");
        System.out.println(toon);
        System.out.println();

        // Decode back to POJO
        // The List<Map> is automatically converted to List<Employee>
        Department decoded = Toon.decode(toon, Department.class);

        System.out.println("Decoded Department:");
        System.out.println("  Name: " + decoded.getName());
        System.out.println("  Location: " + decoded.getLocation());
        System.out.println("  Employees:");
        for (Employee emp : decoded.getEmployees()) {
            System.out.printf("    - ID: %d, Name: %s, Dept: %s%n",
                    emp.getId(), emp.getName(), emp.getDepartment());
        }
        System.out.println();

        System.out.println("✓ Automatic List<Map> → List<Employee> conversion!");
        System.out.println();
    }

    /**
     * Example 5: Java Record (Java 17+)
     */
    private static void example5_JavaRecord() {
        printSectionHeader("Example 5: Java Record");

        // Create a record
        Person person = new Person("David", 35, "Seattle", "Software Engineer");

        // Encode to TOON
        String toon = Toon.encode(person);
        System.out.println("Encoded TOON:");
        System.out.println(toon);
        System.out.println();

        // Decode back to record
        Person decoded = Toon.decode(toon, Person.class);

        System.out.println("Decoded Person:");
        System.out.println("  Name: " + decoded.name());
        System.out.println("  Age: " + decoded.age());
        System.out.println("  City: " + decoded.city());
        System.out.println("  Occupation: " + decoded.occupation());
        System.out.println();
    }

    /**
     * Example 6: POJO with Enum
     */
    private static void example6_PojoWithEnum() {
        printSectionHeader("Example 6: POJO with Enum");

        // Create account with enum
        Account account = new Account();
        account.setUsername("admin");
        account.setRole(Role.ADMIN);
        account.setPermissions(List.of("read", "write", "delete", "admin"));

        // Encode to TOON
        String toon = Toon.encode(account);
        System.out.println("Encoded TOON:");
        System.out.println(toon);
        System.out.println();

        // Decode back to POJO
        Account decoded = Toon.decode(toon, Account.class);

        System.out.println("Decoded Account:");
        System.out.println("  Username: " + decoded.getUsername());
        System.out.println("  Role: " + decoded.getRole());
        System.out.println("  Permissions: " + decoded.getPermissions());
        System.out.println();
    }

    /**
     * Example 7: Complex real-world scenario
     */
    private static void example7_ComplexRealWorld() {
        printSectionHeader("Example 7: Complex Real-World Project");

        // Create a complex project structure
        Project project = new Project();
        project.setId("PROJ-001");
        project.setName("TOON4J");
        project.setDescription("High-performance TOON encoder/decoder for Java");
        project.setTags(List.of("java", "serialization", "llm", "opensource"));

        // Owner
        Employee owner = new Employee();
        owner.setId(1);
        owner.setName("Arun");
        owner.setDepartment("Engineering");
        Address ownerAddress = new Address("123 Tech St", "Silicon Valley", "94025", "USA");
        owner.setAddress(ownerAddress);
        project.setOwner(owner);

        // Contributors
        List<Employee> contributors = new ArrayList<>();

        Employee contributor1 = new Employee();
        contributor1.setId(2);
        contributor1.setName("Alice");
        contributor1.setDepartment("Engineering");
        contributors.add(contributor1);

        Employee contributor2 = new Employee();
        contributor2.setId(3);
        contributor2.setName("Bob");
        contributor2.setDepartment("QA");
        contributors.add(contributor2);

        project.setContributors(contributors);

        // Metadata
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("version", "1.0.0");
        metadata.put("license", "MIT");
        metadata.put("stars", 150);
        metadata.put("forks", 23);
        project.setMetadata(metadata);

        // Encode to TOON
        String toon = Toon.encode(project);
        System.out.println("Encoded TOON:");
        System.out.println(toon);
        System.out.println();

        // Decode back to POJO
        Project decoded = Toon.decode(toon, Project.class);

        System.out.println("Decoded Project:");
        System.out.println("  ID: " + decoded.getId());
        System.out.println("  Name: " + decoded.getName());
        System.out.println("  Description: " + decoded.getDescription());
        System.out.println("  Tags: " + decoded.getTags());
        System.out.println("  Owner: " + decoded.getOwner().getName() +
                " (" + decoded.getOwner().getAddress().getCity() + ")");
        System.out.println("  Contributors: " + decoded.getContributors().size() + " people");
        for (Employee contributor : decoded.getContributors()) {
            System.out.println("    - " + contributor.getName() + " (" + contributor.getDepartment() + ")");
        }
        System.out.println("  Metadata: " + decoded.getMetadata());
        System.out.println();

        // Calculate token savings
        String json = toApproximateJson(project);
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

    // Helper methods

    private static void printSectionHeader(String title) {
        System.out.println("═".repeat(65));
        System.out.println(title);
        System.out.println("═".repeat(65));
    }

    private static String toApproximateJson(Project project) {
        // Simplified JSON approximation for comparison
        return String.format(
            "{\"id\":\"%s\",\"name\":\"%s\",\"description\":\"%s\",\"tags\":%s,\"owner\":{\"id\":%d,\"name\":\"%s\",\"department\":\"%s\",\"address\":{\"street\":\"%s\",\"city\":\"%s\",\"zipCode\":\"%s\",\"country\":\"%s\"}},\"contributors\":[{\"id\":%d,\"name\":\"%s\",\"department\":\"%s\"},{\"id\":%d,\"name\":\"%s\",\"department\":\"%s\"}],\"metadata\":{\"version\":\"%s\",\"license\":\"%s\",\"stars\":%d,\"forks\":%d}}",
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getTags(),
            project.getOwner().getId(),
            project.getOwner().getName(),
            project.getOwner().getDepartment(),
            project.getOwner().getAddress().getStreet(),
            project.getOwner().getAddress().getCity(),
            project.getOwner().getAddress().getZipCode(),
            project.getOwner().getAddress().getCountry(),
            project.getContributors().get(0).getId(),
            project.getContributors().get(0).getName(),
            project.getContributors().get(0).getDepartment(),
            project.getContributors().get(1).getId(),
            project.getContributors().get(1).getName(),
            project.getContributors().get(1).getDepartment(),
            project.getMetadata().get("version"),
            project.getMetadata().get("license"),
            project.getMetadata().get("stars"),
            project.getMetadata().get("forks")
        );
    }
}
