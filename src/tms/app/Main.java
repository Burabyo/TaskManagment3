package tms.app;

import tms.models.*;
import tms.service.*;
import tms.utils.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService(projectService);
        ReportService reportService = new ReportService(projectService);
        ConcurrencyService concurrencyService = new ConcurrencyService(projectService);

        // initial user (Admin) for running UI
        User initialUser = new AdminUser("Jolly Gift", "alice@example.com");

        // create menu and run
        ConsoleMenu menu = new ConsoleMenu(projectService, taskService, reportService, concurrencyService, initialUser);
        menu.run();
    }
}
