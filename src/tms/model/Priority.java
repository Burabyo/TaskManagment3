package tms.model;

/**
 * Priority enum for task importance.
 */
public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static Priority fromString(String s) {
        if (s == null) return MEDIUM;
        switch (s.trim().toUpperCase()) {

            case "LOW": return LOW;
            case "HIGH": return HIGH;
            default: return MEDIUM;


        }
    }

}
