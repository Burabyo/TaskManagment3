package tms.test;

import org.junit.jupiter.api.Test;
import tms.models.Project;
import tms.models.SoftwareProject;
import tms.service.ProjectService;
import tms.utils.FileUtils;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilePersistenceTest {

    @Test
    void testSaveAndLoadProjects() throws IOException {
        ProjectService ps = new ProjectService();
        ps.addProject(new SoftwareProject("Persisted", "desc", 3, 5000));

        // save
        FileUtils.saveAll(ps);

        // load
        Collection<Project> loaded = FileUtils.loadAll();

        assertFalse(loaded.isEmpty());
        assertTrue(
                loaded.stream().anyMatch(p -> p.getName().equals("Persisted"))
        );
    }
}
