package tms.app;

import tms.models.*;
import tms.service.*;
import tms.utils.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        // Initialize core services for managing projects, tasks, reports, and concurrency
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService(projectService);
        ReportService reportService = new ReportService(projectService);
        ConcurrencyService concurrencyService = new ConcurrencyService(projectService);

        // Create initial admin user to start the console UI
        User initialUser = new AdminUser("Jolly Gift", "alice@example.com");

        // Instantiate and run the console menu for user interaction
        ConsoleMenu menu = new ConsoleMenu(projectService, taskService, reportService, concurrencyService, initialUser);
        menu.run();
    }
}
