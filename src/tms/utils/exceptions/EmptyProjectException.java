package tms.utils.exceptions;

/**
 * Thrown when an operation requires tasks but the project has zero tasks.
 */
public class EmptyProjectException extends RuntimeException {
    public EmptyProjectException(String message) {
        super(message);
    }
}
