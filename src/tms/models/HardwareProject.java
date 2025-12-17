package tms.models;

public class HardwareProject extends Project {

    // Constructor with maxTasks
    public HardwareProject(String name, String description, int teamSize, double budget, int maxTasks) {
        super(name, description, teamSize, budget, maxTasks);
    }

    // Constructor without maxTasks
    public HardwareProject(String name, String description, int teamSize, double budget) {
        super(name, description, teamSize, budget);
    }

    @Override
    public String getProjectDetails() {
        return "Hardware"; // identifies project type
    }

    @Override
    public void displayProject() {
        System.out.println("Hardware Project: " + name); // simple display
    }
}
