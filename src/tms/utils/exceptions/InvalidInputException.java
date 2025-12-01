package tms.utils.exceptions;

/**
 * Thrown when user input is invalid (format, allowed values, etc).
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
