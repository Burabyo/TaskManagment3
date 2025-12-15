package tms.utils.exceptions;

/**
 * Thrown when a project with the requested ID is not found.
 */
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
