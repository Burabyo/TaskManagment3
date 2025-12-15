package tms.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract Project class using List<Task>.
 */
public abstract class Project {
    private static int counter = 1;

    private final String id;
    private String name;
    private String description;
    private double budget;
    private int teamSize;

    private final List<Task> tasks = new ArrayList<>();

    protected Project(String name, String description, int teamSize, double budget) {
        this.id = String.format("P%03d", counter++);
        this.name = name;
        this.description = description;
        this.teamSize = teamSize;
        this.budget = budget;
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

    public abstract String getProjectDetails();

    public void displayProject() {
        System.out.printf("Project ID : %s%n", id);
        System.out.printf("Name       : %s%n", name);
        System.out.printf("Type       : %s%n", getProjectDetails());
        System.out.printf("Team Size  : %d%n", teamSize);
        System.out.printf("Budget     : $%,.2f%n", budget);
        System.out.println("Description: " + description);
    }

    // Task operations (synchronized for thread-safety)
    public synchronized boolean addTask(Task t) {
        boolean exists = tasks.stream().anyMatch(x -> x.getName().equalsIgnoreCase(t.getName()));
        if (exists) return false;
        return tasks.add(t);
    }

    public synchronized Optional<Task> findTaskById(String taskId) {
        return tasks.stream().filter(t -> t.getId().equalsIgnoreCase(taskId)).findFirst();
    }

    public synchronized boolean removeTaskById(String taskId) {
        Optional<Task> op = findTaskById(taskId);
        return op.map(tasks::remove).orElse(false);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public int totalTasks() { return tasks.size(); }
    public long completedTasks() { return tasks.stream().filter(Task::isCompleted).count(); }

    public double completionPercentage() {
        int total = totalTasks();
        if (total == 0) return 0.0;
        double perc = (completedTasks() * 100.0) / total;
        return Math.round(perc * 100.0) / 100.0;
    }

    public String summaryLine() {
        return String.format("%s | %-18s | %-10s | team:%2d | $%,8.2f | tasks:%2d | %5.2f%%",
                id, name, getProjectDetails(), teamSize, budget, totalTasks(), completionPercentage());
    }

    // helpers for persistence (simple JSON-like)
    public String tasksToSaveString() {
        return tasks.stream()
                .map(t -> String.format("{\"id\":\"%s\",\"name\":\"%s\",\"status\":\"%s\"}",
                        t.getId(), t.getName().replace("\"","\\\""), t.getStatus().label()))
                .collect(Collectors.joining(","));
    }
}
