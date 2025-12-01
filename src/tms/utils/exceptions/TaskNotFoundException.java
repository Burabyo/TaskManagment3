package tms.utils.exceptions;

/**
 * Thrown when a task with the requested ID is not found inside a project.
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
