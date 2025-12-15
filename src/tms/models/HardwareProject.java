package tms.models;

/**
 * HardwareProject concrete.
 */
public class HardwareProject extends Project {
    public HardwareProject(String name, String description, int teamSize, double budget) {
        super(name, description, teamSize, budget);
    }

    @Override
    public String getProjectDetails() {
        return "Hardware";
    }
}
