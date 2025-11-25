package tms.service;

import tms.models.Project;
import tms.models.Task;

/**
 * This class manages all projects.
 */
public class ProjectManager {

    private Project[] projects;
    private int projectCount;

    public ProjectManager() {
        this.projects = new Project[20]; // max projects
        this.projectCount = 0;
    }

    // Add new project
    public boolean addProject(Project project) {
        if (projectCount >= projects.length) return false;
        projects[projectCount++] = project;
        return true;
    }

    // List all projects
    public Project[] getAllProjects() {
        Project[] list = new Project[projectCount];
        for (int i = 0; i < projectCount; i++) {
            list[i] = projects[i];
        }
        return list;
    }

    // Find project by ID
    public Project getProjectById(String id) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getId().equals(id)) {
                return projects[i];
            }
        }
        return null;
    }

    // Add task to a project
    public boolean addTaskToProject(String projectId, Task task) {
        Project p = getProjectById(projectId);
        if (p == null) return false;
        return p.addTask(task);
    }
}
