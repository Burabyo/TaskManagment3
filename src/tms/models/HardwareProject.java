package tms.models;

public class HardwareProject extends Project {

    public HardwareProject(String name, String description, int teamSize, double budget, int maxTasks) {
        super(name, description, teamSize, budget, maxTasks);
    }

    public HardwareProject(String name, String description, int teamSize, double budget) {
        super(name, description, teamSize, budget);
    }

    @Override
    public String getProjectDetails() {
        return "Hardware";
    }

    @Override
    public void displayProject() {
        System.out.println("Hardware Project: " + name);
    }
}
