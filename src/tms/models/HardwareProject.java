package tms.models;

/**
 * HardwareProject subclass (concrete).
 * Could hold hardware-specific fields later.
 */
public class HardwareProject extends Project {

    public HardwareProject(String name, String description, int teamSize, double budget, int maxTasks) {
        super(name, description, teamSize, budget, maxTasks);
    }

    @Override
    public String getProjectDetails() {
        return "Hardware";
    }
}
