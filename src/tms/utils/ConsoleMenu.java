package tms.utils;

import tms.models.*;
import tms.service.*;
import tms.utils.FileUtils;
import tms.utils.ValidationUtils;
import tms.utils.exceptions.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * Console-based menu for project management operations.
 */
public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ReportService reportService;
    private final ConcurrencyService concurrencyService;
    private User currentUser;

    public ConsoleMenu(ProjectService projectService, TaskService taskService, ReportService reportService, ConcurrencyService concurrencyService, User initialUser) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.reportService = reportService;
        this.concurrencyService = concurrencyService;
        this.currentUser = initialUser;
    }

    public void run() {
        // Load saved projects
        var loaded = FileUtils.loadAll();
        if (!loaded.isEmpty()) projectService.replaceAll(loaded);

        boolean running = true;
        while (running) {
            printHeader();
            System.out.printf("Current User: %s (%s)%n%n", currentUser.getName(), currentUser.getRole());
            System.out.println("Main Menu:");
            System.out.println("1. Manage Projects");
            System.out.println("2. Manage Tasks");
            System.out.println("3. View Status Reports");
            System.out.println("4. Simulate Concurrent Updates");
            System.out.println("5. Switch User");
            System.out.println("6. Save & Exit");
            int choice = ValidationUtils.readInt(scanner, "Enter your choice: ", 1, 6);
            try {
                switch (choice) {
                    case 1 -> manageProjectsMenu();
                    case 2 -> manageProjectsMenu();
                    case 3 -> reportService.printProjectStatusReport();
                    case 4 -> concurrencyMenu();
                    case 5 -> switchUser();
                    case 6 -> { // Save and exit
                        try {
                            FileUtils.saveAll(projectService);
                            System.out.println("Saved. Exiting.");
                        } catch (IOException e) {
                            System.out.println("Failed to save data: " + e.getMessage());
                        }
                        running = false;
                    }
                }
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void printHeader() {
        System.out.println("\n============================");
        System.out.println("  JAVA PROJECT MANAGEMENT SYSTEM (Lab 3)");
        System.out.println("============================\n");
    }

    // Project management menu
    private void manageProjectsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nPROJECT MANAGEMENT");
            System.out.println("1. Create New Project");
            System.out.println("2. View / Filter Projects");
            System.out.println("3. View Project Details");
            System.out.println("4. Back to Main Menu");
            int ch = ValidationUtils.readInt(scanner, "Enter choice: ", 1, 4);
            switch (ch) {
                case 1 -> createProjectFlow();
                case 2 -> filterProjectsFlow();
                case 3 -> viewProjectDetailsFlow();
                case 4 -> back = true;
            }
        }
    }

    private void createProjectFlow() {
        int type = ValidationUtils.readInt(scanner, "Select type: ", 1, 2);
        String name = ValidationUtils.readNonEmpty(scanner, "Enter project name: ");
        String desc = ValidationUtils.readNonEmpty(scanner, "Enter description: ");
        int teamSize = ValidationUtils.readInt(scanner, "Enter team size: ", 1, 1000);
        double budget = ValidationUtils.readDouble(scanner, "Enter budget: ", 0, Double.MAX_VALUE);

        Project p = (type == 1)
                ? new SoftwareProject(name, desc, teamSize, budget)
                : new HardwareProject(name, desc, teamSize, budget);
        projectService.addProject(p);
        System.out.printf("Project created with ID %s.%n", p.getId());
    }

    private void filterProjectsFlow() {
        int choice = ValidationUtils.readInt(scanner, "Enter filter choice: ", 1, 4);
        switch (choice) {
            case 1 -> displayProjects(projectService.getAllProjects());
            case 2 -> displayProjects(projectService.filterByType("Software"));
            case 3 -> displayProjects(projectService.filterByType("Hardware"));
            case 4 -> {
                double min = ValidationUtils.readDouble(scanner, "Enter min budget: ", 0, Double.MAX_VALUE);
                double max = ValidationUtils.readDouble(scanner, "Enter max budget: ", 0, Double.MAX_VALUE);
                displayProjects(projectService.searchByBudgetRange(min, max));
            }
        }
    }

    private void displayProjects(Iterable<Project> projects) {
        var it = projects.iterator();
        if (!it.hasNext()) { System.out.println("No projects found."); return; }
        System.out.println("\nPROJECT LIST:");
        for (Project p : projects) {
            System.out.println(p.summaryLine());
        }
    }

    private void viewProjectDetailsFlow() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID: ");
        Project p = projectService.findById(pid);
        p.displayProject();
        System.out.println("\nAssociated Tasks:");
        var tasks = p.getTasks();
        if (tasks.isEmpty()) System.out.println("No tasks yet.");
        else {
            System.out.printf("%-8s %-25s %-12s %-10s %-6s%n", "TASKID", "NAME", "STATUS", "ASSIGNED", "HOURS");
            for (Task t : tasks) {
                System.out.printf("%-8s %-25s %-12s %-10s %-6.1f%n",
                        t.getId(), t.getName(), t.getStatus().label(), t.getAssignedUserId()==null?"-":t.getAssignedUserId(), t.getHoursEstimate());
            }
        }
        System.out.printf("Completion Rate: %.2f%%%n", p.completionPercentage());
    }

    // Switch user type (Admin/Regular)
    private void switchUser() {
        int choice = ValidationUtils.readInt(scanner, "Choose user type: ", 1, 2);
        String name = ValidationUtils.readNonEmpty(scanner, "Enter name: ");
        String email = ValidationUtils.readNonEmpty(scanner, "Enter email: ");
        currentUser = (choice == 1) ? new AdminUser(name, email) : new RegularUser(name, email);
        System.out.printf("Switched to %s (%s).%n", currentUser.getName(), currentUser.getRole());
    }

    private void concurrencyMenu() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID for simulation: ");
        concurrencyService.simulateConcurrentUpdates(pid); // simulate multi-threaded updates
    }
}
