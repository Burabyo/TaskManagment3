package tms.service;

import tms.models.Project;
import tms.models.Task;
import tms.models.Status;

/**
 * TaskService delegates task operations to the Project objects.
 * Keeps logic concise and ensures validation is centralized when needed.
 */
public class TaskService {

    private final ProjectService projectService;

    public TaskService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public boolean addTaskToProject(String projectId, Task t) {
        Project p = projectService.findById(projectId);
        if (p == null) return false;
        return p.addTask(t); // Project handles duplicates / capacity
    }

    public boolean updateTaskStatus(String projectId, String taskId, Status newStatus) {
        Project p = projectService.findById(projectId);
        if (p == null) return false;
        Task t = p.findTaskById(taskId);
        if (t == null) return false;
        t.setStatus(newStatus);
        return true;
    }

    public boolean removeTask(String projectId, String taskId) {
        Project p = projectService.findById(projectId);
        if (p == null) return false;
        return p.removeTaskById(taskId);
    }
}
