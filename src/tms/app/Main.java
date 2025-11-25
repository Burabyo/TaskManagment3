package tms.app;

import tms.models.*;
import tms.service.*;
import tms.utils.ConsoleMenu;

import java.util.Scanner;

/**
 * Application entry point.
 * Wire services and start ConsoleMenu.
 */
public class Main {
    public static void main(String[] args) {
        // Scanner shared across the app
        Scanner scanner = new Scanner(System.in);

        // Services (arrays sizes chosen to satisfy assignment minimums and some room)
        ProjectService projectService = new ProjectService(100); // up to 100 projects
        TaskService taskService = new TaskService(projectService);
        ReportService reportService = new ReportService(projectService);

        // Seed sample projects and tasks
        projectService.seedSampleData();

        // Default starting user (Admin) to allow full interactions
        User defaultUser = new AdminUser("Alice Johnson", "alice@example.com");

        // Start console menu
        ConsoleMenu menu = new ConsoleMenu(scanner, projectService, taskService, reportService, defaultUser);
        menu.run();

        // Clean up
        scanner.close();
        System.out.println("Application exited.");
    }
}
