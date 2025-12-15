package tms.service;

import tms.models.Project;

import java.util.Collection;

/**
 * Simple report service to print project status report.
 */
public class ReportService {
    private final ProjectService projectService;

    public ReportService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void printProjectStatusReport() {
        Collection<Project> projects = projectService.getAllProjects();
        System.out.println("\nII PROJECT STATUS REPORT II\n");
        System.out.printf("%-8s %-20s %-6s %-9s %-8s%n", "PROJECT", "NAME", "TASKS", "COMPLETED", "PROGRESS");
        double avgTotal = 0.0;
        int count = 0;
        for (Project p : projects) {
            int total = p.totalTasks();
            long completed = p.completedTasks();
            double progress = p.completionPercentage();
            System.out.printf("%-8s %-20s %-6d %-9d %6.2f%%%n", p.getId(), p.getName(), total, completed, progress);
            avgTotal += progress;
            count++;
        }
        double avg = count == 0 ? 0.0 : avgTotal / count;
        System.out.printf("%nAVERAGE COMPLETION: %.2f%%%n%n", avg);
    }
}
