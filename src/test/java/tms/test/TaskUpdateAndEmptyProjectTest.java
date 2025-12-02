package tms.test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tms.models.Project;
import tms.models.SoftwareProject;
import tms.models.Task;
import tms.models.Status;
import tms.service.ProjectService;
import tms.service.TaskService;
import tms.utils.exceptions.TaskNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class TaskUpdateAndEmptyProjectTest {

    @Test
    void testUpdateTaskStatus_success() {
        ProjectService ps = new ProjectService(10);
        Project p = new SoftwareProject("Gamma", "desc", 4, 8000, 10);
        ps.addProject(p);
        Task t = new Task("T1", Status.PENDING);
        TaskService ts = new TaskService(ps);
        ts.addTaskToProject(p.getId(), t);
        assertTrue(ts.updateTaskStatus(p.getId(), t.getId(), Status.COMPLETED));
        assertEquals(Status.COMPLETED, t.getStatus());
    }

    @Test
    void testEmptyProject_throwsWhenUpdatingNonExistentTask() {
        ProjectService ps = new ProjectService(10);
        Project p = new SoftwareProject("Empty", "desc", 2, 1000, 5);
        ps.addProject(p);
        // ensure there are no tasks
        assertEquals(0, p.totalTasks());
        // updating a non-existent task should throw TaskNotFoundException
        TaskService ts = new TaskService(ps);
        assertThrows(TaskNotFoundException.class, () -> ts.updateTaskStatus(p.getId(), "TSK999", Status.COMPLETED));
    }
}
