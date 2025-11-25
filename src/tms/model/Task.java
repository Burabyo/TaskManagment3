package tms.model;

/**
 * Represents a single task inside a project.
 * Each task has:
 * - a title
 * - a description
 * - a due date (kept as String for now)
 * - a completion flag
 */
public class Task {

    private String title;
    private String description;
    private String dueDate;
    private boolean completed;

    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false; // default state
    }

    // ----- GETTERS -----
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    // ----- SETTERS -----
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    @Override
    public String toString() {
        return "Task: " + title +
                " | Description: " + description +
                " | Due: " + dueDate +
                " | Completed: " + completed;
    }
}
