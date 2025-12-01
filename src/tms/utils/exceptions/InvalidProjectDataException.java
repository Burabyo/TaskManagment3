package tms.utils.exceptions;

/**
 * Thrown when project data is invalid (negative budget, invalid team size, etc).
 */
public class InvalidProjectDataException extends RuntimeException {
    public InvalidProjectDataException(String message) {
        super(message);
    }
}
