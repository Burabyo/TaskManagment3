package tms.service;

import tms.models.Project;
import tms.models.SoftwareProject;
import tms.models.HardwareProject;

/**
 * ProjectService stores projects in an array (no collections).
 * Provides create, list, search (by id/type/budget range) operations.
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

    public boolean addProject(Project p) {
        if (projectCount >= maxProjects) return false;
        projects[projectCount++] = p;
        return true;
    }

    public Project findById(String id) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getId().equalsIgnoreCase(id)) return projects[i];
        }
        return null;
    }

    public Project[] getAllProjects() {
        Project[] out = new Project[projectCount];
        for (int i = 0; i < projectCount; i++) out[i] = projects[i];
        return out;
    }

    // Filter by type: "Software" or "Hardware"
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

    // Budget range search
    public Project[] searchByBudgetRange(double min, double max) {
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

    // Seed sample data (minimum 5 projects as required)
    public void seedSampleData() {
        // use maxTasks per project = 50 (example)
        addProject(new SoftwareProject("Alpha Tracker", "Task tracking app for startups", 5, 15000, 50));
        addProject(new HardwareProject("IoT Sensor Kit", "Sensor prototype for smart devices", 3, 10000, 50));
        addProject(new SoftwareProject("Billing Service", "Invoice microservice", 4, 20000, 50));
        addProject(new HardwareProject("Drone Platform", "Prototype drone chassis", 6, 45000, 50));
        addProject(new SoftwareProject("Mobile Wallet", "Payment app MVP", 7, 30000, 50));
    }
}
