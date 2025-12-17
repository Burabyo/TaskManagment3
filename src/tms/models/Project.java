package tms.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Project {

    protected String id;
    protected String name;
    protected String description;
    protected int teamSize;
    protected double budget;
    protected int maxTasks;

    protected List<Task> tasks = new ArrayList<>();

    public Project(String name, String description, int teamSize, double budget, int maxTasks) {
        this.id = "P" + UUID.randomUUID().toString().substring(0, 4);
        this.name = name;
        this.description = description;
        this.teamSize = teamSize;
        this.budget = budget;
        this.maxTasks = maxTasks;
    }

    public Project(String name, String description, int teamSize, double budget) {
        this(name, description, teamSize, budget, 10);
    }

    /* ---------------- TASK METHODS ---------------- */

    public void addTask(Task task) {
        if (tasks.size() >= maxTasks)
            throw new IllegalStateException("Maximum tasks reached");
        tasks.add(task);
    }

    public List<Task> getTasks() { return tasks; }

    /** ✅ FIX: method required by ReportService */
    public int totalTasks() {
        return tasks.size();
    }

    public long completedTasks() {
        return tasks.stream()
                .filter(t -> t.getStatus() == Status.COMPLETED)
                .count();
    }

    public double completionPercentage() {
        return tasks.isEmpty() ? 0 : (completedTasks() * 100.0) / tasks.size();
    }

    public String summaryLine() {
        return id + " | " + name + " | " +
                String.format("%.2f%%", completionPercentage());
    }

    /* ---------------- FILE SAVE HELPER ---------------- */

    public String tasksToSaveString() {
        return tasks.stream()
                .map(t -> String.format(
                        "{\"id\":\"%s\",\"name\":\"%s\",\"status\":\"%s\"}",
                        t.getId(), t.getName(), t.getStatus()))
                .collect(Collectors.joining(","));
    }

    /* ---------------- GETTERS ---------------- */

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getBudget() { return budget; }
    public int getTeamSize() { return teamSize; }

    /* ---------------- ABSTRACT ---------------- */

    public abstract String getProjectDetails();
    public abstract void displayProject();
}
