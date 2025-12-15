package tms.service;

import tms.models.Project;

/**
 * Simple reporting service that formats and computes project statistics.
 */
public class ReportService {

    private final ProjectService projectService;

    public ReportService(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Print status report to console (used by ConsoleMenu)
    public void printProjectStatusReport() {
        Project[] all = projectService.getAllProjects();
        System.out.printf("%-8s %-18s %-6s %-9s %-12s%n", "PROJECT", "NAME", "TASKS", "COMPLETED", "PROGRESS(%)");
        double sum = 0;
        for (Project p : all) {
            int total = p.totalTasks();
            int completed = p.completedTasks();
            double perc = p.completionPercentage();
            System.out.printf("%-8s %-18s %-6d %-9d %-12.2f%n", p.getId(), p.getName(), total, completed, perc);
            sum += perc;
        }
        double avg = all.length == 0 ? 0 : sum / all.length;
        System.out.printf("%nAVERAGE COMPLETION: %.2f%%%n", avg);
    }
}
