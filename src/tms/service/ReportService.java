package tms.service;

import tms.models.Project;

public class ReportService {
    private final ProjectService projectService; // dependency to access projects

    public ReportService(ProjectService ps) {
        this.projectService = ps;
    }

    // Prints a summary of all projects
    public void printProjectStatusReport() {
        for (Project p : projectService.getAllProjects()) {
            System.out.printf(
                    "%s %s %d %d %.2f%%%n",
                    p.getId(),
                    p.getName(),
                    p.totalTasks(),
                    p.completedTasks(),
                    p.completionPercentage()
            );
        }
    }
}
