package tms.service;

import tms.models.Project;
import tms.models.Task;
import tms.models.Status;
import tms.utils.exceptions.TaskNotFoundException;

import java.util.Optional;

/**
 * TaskService operates through ProjectService; uses project's task list.
 */
public class TaskService {
    private final ProjectService projectService;

    public TaskService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public boolean addTaskToProject(String projectId, Task t) {
        Project p = projectService.findById(projectId);
        return p.addTask(t);
    }

    public boolean updateTaskStatus(String projectId, String taskId, Status newStatus) {
        Project p = projectService.findById(projectId);
        Optional<Task> op = p.findTaskById(taskId);
        if (op.isEmpty()) throw new TaskNotFoundException("Task " + taskId + " not found in " + projectId);
        op.get().setStatus(newStatus);
        return true;
    }

    public boolean removeTask(String projectId, String taskId) {
        Project p = projectService.findById(projectId);
        boolean removed = p.removeTaskById(taskId);
        if (!removed) throw new TaskNotFoundException("Task " + taskId + " not found in " + projectId);
        return true;
    }
}
