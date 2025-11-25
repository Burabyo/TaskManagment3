package tms.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This is the heart of my project as it will describe the TASK.
 */
public class Task {
    private static final DateTimeFormatter DF = DateTimeFormatter.ISO_LOCAL_DATE;

    private int id; // unique id assigned by TaskManager for every task
    private String title;
    private String description;
    private LocalDate dueDate; // can be null if user doesn't set the date
    private Priority priority;
    private Status status;

    public Task(int id, String title, String description, LocalDate dueDate, Priority priority, Status status) {
        this.id = id;
        this.title = title == null ? "" : title;
        this.description = description == null ? "" : description;
        this.dueDate = dueDate;
        this.priority = priority == null ? Priority.MEDIUM : priority;
        this.status = status == null ? Status.TODO : status;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title == null ? "" : title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description == null ? "" : description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        String due = (dueDate == null) ? "N/A" : dueDate.format(DF);
        return String.format("ID: %d | %s | %s | Due: %s | Priority: %s | Status: %s",
                id, title, description, due, priority, status);
    }

    /**
     * Converting to CSV line for storage.
     * Format: id|title|description|yyyy-MM-dd|null|priority|status
     */
    public String toCsvLine() {
        String due = dueDate == null ? "" : dueDate.format(DF);
        // Replace pipeline char in title/desc to avoid breaking the format
        String safeTitle = title.replace("|", " ");
        String safeDesc = description.replace("|", " ");
        return id + "|" + safeTitle + "|" + safeDesc + "|" + due + "|" + priority + "|" + status;
    }

    /**
     * Parse from a csv line created by toCsvLine()
     */
    public static Task fromCsvLine(String line) {
        String[] parts = line.split("\\|", -1); // -1 to keep empty trailing elements
        if (parts.length < 6) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String desc = parts[2];
            LocalDate due = parts[3].isEmpty() ? null : LocalDate.parse(parts[3], DF);
            Priority p = Priority.fromString(parts[4]);
            Status s = Status.fromString(parts[5]);
            return new Task(id, title, desc, due, p, s);
        } catch (Exception e) {
            return null; // caller will skip malformed lines
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
