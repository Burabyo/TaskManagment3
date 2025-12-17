package tms.test;

import org.junit.jupiter.api.Test;
import tms.models.Project;
import tms.models.SoftwareProject;
import tms.service.ProjectService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceStreamTest {

    @Test
    void testFilterByType_softwareProjectsOnly() {
        ProjectService ps = new ProjectService();

        ps.addProject(new SoftwareProject("Alpha", "desc", 3, 10000));
        ps.addProject(new SoftwareProject("Beta", "desc", 2, 8000));

        List<Project> result = ps.filterByType("Software");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getProjectDetails().equals("Software")));
    }

    @Test
    void testSearchByBudgetRange() {
        ProjectService ps = new ProjectService();

        ps.addProject(new SoftwareProject("Low", "desc", 2, 2000));
        ps.addProject(new SoftwareProject("Mid", "desc", 3, 7000));
        ps.addProject(new SoftwareProject("High", "desc", 4, 15000));

        List<Project> result = ps.searchByBudgetRange(5000, 10000);

        assertEquals(1, result.size());
        assertEquals("Mid", result.get(0).getName());
    }
}
