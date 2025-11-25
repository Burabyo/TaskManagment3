package tms.models;

/**
 * SoftwareProject subclass (concrete).
 * Could hold software-specific fields later (e.g., language), but minimal now.
 */
public class SoftwareProject extends Project {

    public SoftwareProject(String name, String description, int teamSize, double budget, int maxTasks) {
        super(name, description, teamSize, budget, maxTasks);
    }

    @Override
    public String getProjectDetails() {
        return "Software";
    }
}
