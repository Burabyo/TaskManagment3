package tms.models;

/**
 * Enum representing allowed task statuses.
 * Using an enum avoids invalid status strings in many places.
 */
public enum Status {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static Status fromString(String s) {
        if (s == null) return null;
        String v = s.trim().toLowerCase();
        return switch (v) {
            case "pending" -> PENDING;
            case "in progress", "in_progress", "inprogress" -> IN_PROGRESS;
            case "completed" -> COMPLETED;
            default -> null;
        };
    }
}
