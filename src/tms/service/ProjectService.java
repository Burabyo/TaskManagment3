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

    // Seed sample data
    public void seedSampleData() {
        // use maxTasks per project = 50 (example)
        addProject(new SoftwareProject(
                "Smart Garden Monitor",
                "Mobile app and sensor system for monitoring soil and sunlight",
                4,         // team size
                8500,      // budget
                50         // max tasks
        ));

        addProject(new HardwareProject(
                "Drone Delivery Chassis",
                "Lightweight drone body for parcel delivery",
                3,
                12000,
                50
        ));

        addProject(new SoftwareProject(
                "Student Attendance Tracker",
                "Web platform for QR-based attendance management",
                5,
                6000,
                50
        ));

        addProject(new HardwareProject(
                "Solar Street Light Controller",
                "Electronic module for managing solar lamp brightness and battery usage",
                4,
                9000,
                50
        ));

        addProject(new SoftwareProject(
                "Fitness Meal Planner",
                "Nutrition app that generates weekly meal plans",
                3,
                4500,
                50
        ));

    }
}
