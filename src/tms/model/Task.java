package tms.model;

/**
 * Represents a single task inside a project.
 */
public class Task {

    private static int counter = 1;   // Auto-generate task IDs

    private String id;
    private String name;
    private String status; // "Pending", "In Progress", "Completed"

    public Task(String name, String status) {
        this.id = generateId();
        this.name = name;
        this.status = status;
    }

    private String generateId() {
        return String.format("T%03d", counter++);
    }

    // Getters & setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
