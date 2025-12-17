package tms.models;

public enum Status {

    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    /** Used by UI / reports */
    public String label() {
        return label;
    }

    /** Used when loading from file or parsing input */
    public static Status fromString(String s) {
        if (s == null) return PENDING;
        return switch (s.toLowerCase()) {
            case "completed" -> COMPLETED;
            case "in progress", "in_progress" -> IN_PROGRESS;
            default -> PENDING;
        };
    }
}
