package tms.models;

public class SoftwareProject extends Project {

    // Constructor with maxTasks
    public SoftwareProject(String name, String description, int teamSize, double budget, int maxTasks) {
        super(name, description, teamSize, budget, maxTasks);
    }

    // Constructor with default maxTasks
    public SoftwareProject(String name, String description, int teamSize, double budget) {
        super(name, description, teamSize, budget);
    }

    @Override
    public String getProjectDetails() {
        return "Software"; // identifies project type
    }

    @Override
    public void displayProject() {
        System.out.println("Software Project: " + name);
    }
}
