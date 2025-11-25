package tms.model;

import java.util.Arrays;

/**
 * Holds project information and an array of tasks associated with it.
 */
public class Project {

    private static int counter = 1;

    private String id;
    private String name;
    private String description;
    private Task[] tasks;    // fixed-size array
    private int taskCount;   // how many tasks stored

    public Project(String name, String description) {
        this.id = generateId();
        this.name = name;
        this.description = description;
        this.tasks = new Task[20]; // limit per project
        this.taskCount = 0;
    }

    private String generateId() {
        return String.format("P%03d", counter++);
    }

    // Add a task
    public boolean addTask(Task task) {
        if (taskCount >= tasks.length) {
            return false; // array full
        }
        tasks[taskCount++] = task;
        return true;
    }

    // Find task by ID
    public Task getTaskById(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId().equals(taskId)) {
                return tasks[i];
            }
        }
        return null;
    }

    // Calculate completion percentage
    public double getCompletionRate() {
        if (taskCount == 0) return 0;

        int completed = 0;
        for (int i = 0; i < taskCount; i++) {
            if ("Completed".equalsIgnoreCase(tasks[i].getStatus())) {
                completed++;
            }
        }
        return (completed * 100.0) / taskCount;
    }

    // Getters
    public String getId() { return id; }
    public Task[] getTasks() { return Arrays.copyOf(tasks, taskCount); }
    public int getTaskCount() { return taskCount; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
