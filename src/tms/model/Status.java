package tms.model;

/**
 * Task status
 */
public enum Status {
    TODO,
    IN_PROGRESS,
    DONE;

    public static Status fromString(String s) {
        if (s == null) return TODO;
        switch (s.trim().toUpperCase()) {
            case "IN_PROGRESS": return IN_PROGRESS;
            case "DONE": return DONE;
            default: return TODO;
        }
    }
}

