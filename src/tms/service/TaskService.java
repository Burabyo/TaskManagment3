package tms.service;

import tms.models.Project;
import tms.models.Task;
import tms.models.Status;
import tms.utils.exceptions.ProjectNotFoundException;
import tms.utils.exceptions.TaskNotFoundException;

/**
 * TaskService delegates task operations to the Project objects.
 * Now throws TaskNotFoundException when a task is missing.
 */
public class TaskService {

    private final ProjectService projectService;

    public TaskService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public boolean addTaskToProject(String projectId, Task t) {
        Project p = projectService.findById(projectId); // will throw ProjectNotFoundException if missing
        return p.addTask(t); // Project handles duplicates/capacity and returns boolean
    }

    public boolean updateTaskStatus(String projectId, String taskId, Status newStatus) {
        Project p = projectService.findById(projectId);
        Task t = p.findTaskById(taskId);
        if (t == null) {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found in project " + projectId + ".");
        }
        t.setStatus(newStatus);
        return true;
    }

    public boolean removeTask(String projectId, String taskId) {
        Project p = projectService.findById(projectId);
        boolean removed = p.removeTaskById(taskId);
        if (!removed) {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found in project " + projectId + ".");
        }
        return true;
    }
}
