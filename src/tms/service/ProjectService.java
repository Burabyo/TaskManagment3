package tms.service;

import tms.models.Project;
import tms.utils.exceptions.ProjectNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages all projects in memory.
 */
public class ProjectService {

    private final Map<String, Project> projects = new LinkedHashMap<>();

    /**  Default constructor (used by app) */
    public ProjectService() { }

    /** Compatibility constructor */
    public ProjectService(int ignoredCapacity) {
        // capacity no longer needed, but constructor kept for tests
    }

    public void addProject(Project p) {
        projects.put(p.getId(), p);
    }

    public Project findById(String id) {
        Project p = projects.get(id);
        if (p == null)
            throw new ProjectNotFoundException("Project not found: " + id);
        return p;
    }

    public Collection<Project> getAllProjects() {
        return projects.values();
    }

    public List<Project> filterByType(String type) {
        return projects.values().stream()
                .filter(p -> p.getProjectDetails().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Project> searchByBudgetRange(double min, double max) {
        return projects.values().stream()
                .filter(p -> p.getBudget() >= min && p.getBudget() <= max)
                .collect(Collectors.toList());
    }

    public void replaceAll(Collection<Project> list) {
        projects.clear();
        for (Project p : list) {
            projects.put(p.getId(), p);
        }
    }
}
