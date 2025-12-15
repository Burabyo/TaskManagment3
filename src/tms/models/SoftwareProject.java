package tms.models;

/**
 * SoftwareProject concrete.
 */
public class SoftwareProject extends Project {
    public SoftwareProject(String name, String description, int teamSize, double budget) {
        super(name, description, teamSize, budget);
    }

    @Override
    public String getProjectDetails() {
        return "Software";
    }
}
