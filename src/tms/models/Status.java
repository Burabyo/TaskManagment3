package tms.models;

/**
 * Task Status enumeration with helper methods.
 */
public enum Status {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String label;

    Status(String label) { this.label = label; }

    public String label() { return label; }

    public static Status fromString(String s) {
        if (s == null) return PENDING;
        String n = s.trim().toLowerCase();
        return switch (n) {
            case "completed", "complete" -> COMPLETED;
            case "in progress", "in_progress", "inprogress" -> IN_PROGRESS;
            default -> PENDING;
        };
    }
}
