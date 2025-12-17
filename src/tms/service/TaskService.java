package tms.service;

import tms.models.Project;
import tms.models.Status;
import tms.models.Task;
import tms.utils.exceptions.TaskNotFoundException;

public class TaskService {
    private final ProjectService projectService;

    public TaskService(ProjectService ps) {
        this.projectService = ps;
    }

    /**
     * Adds a new task to a project.
     */
    public void addTaskToProject(String projectId, Task task) {
        Project p = projectService.findById(projectId);
        p.addTask(task);
    }

    /**
     * Updates the status of a task in a project.
     */
    public boolean updateTaskStatus(String projectId, String taskId, Status status) {
        Project p = projectService.findById(projectId);
        for (Task t : p.getTasks()) {
            if (t.getId().equals(taskId)) {
                t.setStatus(status);
                return true;
            }
        }
        throw new TaskNotFoundException("Task not found: " + taskId);
    }

    /**
     * Removes a task from a project.
     */
    public void removeTask(String projectId, String taskId) {
        Project p = projectService.findById(projectId);
        Task toRemove = null;
        for (Task t : p.getTasks()) {
            if (t.getId().equals(taskId)) {
                toRemove = t;
                break;
            }
        }
        if (toRemove != null) {
            p.getTasks().remove(toRemove);
        } else {
            throw new TaskNotFoundException("Task not found: " + taskId);
        }
    }
}
