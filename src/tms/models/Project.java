package tms.models;

import java.util.Arrays;

/**
 * Abstract Project class required by assignment.

 * Fields required: id, name, description, budget, teamSize.
 * Stores tasks in an array (no collections) as the lab requires.

 * Subclasses must implement getProjectDetails() to include type-specific info.
 */
public abstract class Project {
    private static int counter = 1;

    private final String id;
    private String name;
    private String description;
    private double budget;
    private int teamSize;

    // Each project stores tasks in an array (fixed maximum per project)
    private final Task[] tasks;
    private int taskCount;

    protected Project(String name, String description, int teamSize, double budget, int maxTasks) {
        this.id = String.format("PRJ%03d", counter++);
        this.name = name;
        this.description = description;
        this.teamSize = teamSize;
        this.budget = budget;
        this.tasks = new Task[maxTasks];
        this.taskCount = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public int getTeamSize() { return teamSize; }
    public void setTeamSize(int teamSize) { this.teamSize = teamSize; }

    // Abstract method: subclass-specific details
    public abstract String getProjectDetails();

    // Display method (concrete)
    public void displayProject() {
        System.out.printf("Project ID: %s%n", id);
        System.out.printf("Name      : %s%n", name);
        System.out.printf("Type      : %s%n", getProjectDetails());
        System.out.printf("Team Size : %d%n", teamSize);
        System.out.printf("Budget    : $%,.2f%n", budget);
        System.out.println("Description: " + description);
    }

    // Task array operations (array-only)
    public boolean addTask(Task t) {
        if (taskCount >= tasks.length) return false;
        // Prevent duplicate task names
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getName().equalsIgnoreCase(t.getName())) return false;
        }
        tasks[taskCount++] = t;
        return true;
    }

    public Task findTaskById(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId().equalsIgnoreCase(taskId)) return tasks[i];
        }
        return null;
    }

    public boolean removeTaskById(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId().equalsIgnoreCase(taskId)) {
                // shift left
                for (int j = i; j < taskCount - 1; j++) tasks[j] = tasks[j + 1];
                tasks[--taskCount] = null;
                return true;
            }
        }
        return false;
    }

    public Task[] getTasks() {
        return Arrays.copyOf(tasks, taskCount);
    }

    public int totalTasks() { return taskCount; }

    public int completedTasks() {
        int c = 0;
        for (int i = 0; i < taskCount; i++) if (tasks[i].isCompleted()) c++;
        return c;
    }

    public double completionPercentage() {
        if (taskCount == 0) return 0.0;
        double perc = (completedTasks() * 100.0) / taskCount;
        return Math.round(perc * 100.0) / 100.0; // round to 2 decimals
    }

    // Short summary for lists
    public String summaryLine() {
        return String.format("%s | %-18s | Type:%-8s | Team:%2d | $%,8.2f | Tasks:%2d | %5.2f%%",
                id, name, getProjectDetails(), teamSize, budget, totalTasks(), completionPercentage());
    }
}
