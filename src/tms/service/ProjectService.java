package tms.service;

import tms.models.Project;
import tms.models.SoftwareProject;
import tms.models.HardwareProject;
import tms.utils.exceptions.InvalidProjectDataException;
import tms.utils.exceptions.ProjectNotFoundException;

/**
 * ProjectService stores projects in an array (no collections).
 * Now validates project data and throws exceptions on invalid input.
 */
public class ProjectService {
    private final Project[] projects;
    private int projectCount;
    private final int maxProjects;

    public ProjectService(int maxProjects) {
        this.maxProjects = maxProjects;
        this.projects = new Project[maxProjects];
        this.projectCount = 0;
    }

    /**
     * Add a project with basic validation.
     */
    public boolean addProject(Project p) {
        if (p.getBudget() < 0) {
            throw new InvalidProjectDataException("Budget cannot be negative.");
        }
        if (p.getTeamSize() <= 0) {
            throw new InvalidProjectDataException("Team size must be greater than 0.");
        }
        if (projectCount >= maxProjects) return false;
        projects[projectCount++] = p;
        return true;
    }

    public Project findById(String id) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getId().equalsIgnoreCase(id)) return projects[i];
        }
        throw new ProjectNotFoundException("Project with ID " + id + " not found.");
    }

    public Project[] getAllProjects() {
        Project[] out = new Project[projectCount];
        for (int i = 0; i < projectCount; i++) out[i] = projects[i];
        return out;
    }

    public Project[] filterByType(String type) {
        Project[] tmp = new Project[projectCount];
        int c = 0;
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getProjectDetails().equalsIgnoreCase(type)) tmp[c++] = projects[i];
        }
        Project[] out = new Project[c];
        System.arraycopy(tmp, 0, out, 0, c);
        return out;
    }

    public Project[] searchByBudgetRange(double min, double max) {
        if (max < min) throw new InvalidProjectDataException("Max budget must be >= min budget.");
        Project[] tmp = new Project[projectCount];
        int c = 0;
        for (int i = 0; i < projectCount; i++) {
            double b = projects[i].getBudget();
            if (b >= min && b <= max) tmp[c++] = projects[i];
        }
        Project[] out = new Project[c];
        System.arraycopy(tmp, 0, out, 0, c);
        return out;
    }

    public void seedSampleData() {

        addProject(new SoftwareProject("Smart Garden Monitor", "Mobile app and sensor system", 4, 8500, 50));
        addProject(new HardwareProject("Drone Delivery Chassis", "Lightweight drone body", 3, 12000, 50));
        addProject(new SoftwareProject("Student Attendance Tracker", "QR-based attendance", 5, 6000, 50));
        addProject(new HardwareProject("Solar Street Light Controller", "Battery and brightness control", 4, 9000, 50));
        addProject(new SoftwareProject("Fitness Meal Planner", "Weekly meal plans", 3, 4500, 50));
    }
}
