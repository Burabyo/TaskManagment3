package tms.service;

import tms.models.Project;
import tms.utils.exceptions.InvalidProjectDataException;
import tms.utils.exceptions.ProjectNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Project service backed by HashMap for quick lookup.
 */
public class ProjectService {
    private final Map<String, Project> projects = new LinkedHashMap<>();

    public ProjectService() { }

    public boolean addProject(Project p) {
        if (p.getBudget() < 0) throw new InvalidProjectDataException("Budget cannot be negative.");
        if (p.getTeamSize() <= 0) throw new InvalidProjectDataException("Team size must be > 0.");
        projects.put(p.getId(), p);
        return true;
    }

    public Project findById(String id) {
        Project p = projects.get(id);
        if (p == null) throw new ProjectNotFoundException("Project " + id + " not found.");
        return p;
    }

    public Collection<Project> getAllProjects() { return projects.values(); }

    public List<Project> filterByType(String type) {
        return projects.values().stream()
                .filter(p -> p.getProjectDetails().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Project> searchByBudgetRange(double min, double max) {
        if (max < min) throw new InvalidProjectDataException("Max must be >= min.");
        return projects.values().stream()
                .filter(p -> p.getBudget() >= min && p.getBudget() <= max)
                .collect(Collectors.toList());
    }

    public List<Project> completedAbove(double percent) {
        return projects.values().stream()
                .filter(p -> p.completionPercentage() > percent)
                .collect(Collectors.toList());
    }

    public void replaceAll(Collection<Project> list) {
        projects.clear();
        for (Project p : list) projects.put(p.getId(), p);
    }
}
