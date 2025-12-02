package tms.test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tms.models.Project;
import tms.models.SoftwareProject;
import tms.models.Task;
import tms.models.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectCompletionTest {

    @Test

    void testCalculateCompletionPercentage_halfCompleted() {
        Project p = new SoftwareProject("Alpha", "desc", 3, 10000, 10);
        p.addTask(new Task("Design DB", Status.COMPLETED));
        p.addTask(new Task("Implement API", Status.PENDING));
        double perc = p.completionPercentage();
        assertEquals(50.0, perc, 0.001);
    }

    @Test
    void testCalculateCompletionPercentage_allCompleted() {
        Project p = new SoftwareProject("Beta", "desc", 3, 5000, 10);
        p.addTask(new Task("Task1", Status.COMPLETED));
        p.addTask(new Task("Task2", Status.COMPLETED));
        double perc = p.completionPercentage();
        assertEquals(100.0, perc, 0.001);
    }
}
