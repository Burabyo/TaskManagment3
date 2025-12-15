package tms.models;

/**
 * Abstract user base class.
 * Provides auto-generated unique user IDs and common fields.
 */
public abstract class User {
    private static int counter = 1;

    private final String id;
    private final String name;
    private final String email;

    public User(String name, String email) {
        this.id = String.format("U%03d", counter++);
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract String getRole();
}
