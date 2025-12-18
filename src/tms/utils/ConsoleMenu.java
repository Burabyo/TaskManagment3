package tms.utils;

import tms.models.*;
import tms.service.*;
import tms.utils.exceptions.*;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ReportService reportService;
    private final ConcurrencyService concurrencyService;
    private User currentUser;

    public ConsoleMenu(ProjectService projectService,
                       TaskService taskService,
                       ReportService reportService,
                       ConcurrencyService concurrencyService,
                       User initialUser) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.reportService = reportService;
        this.concurrencyService = concurrencyService;
        this.currentUser = initialUser;
    }

    public void run() {
        var loaded = FileUtils.loadAll();
        if (!loaded.isEmpty()) projectService.replaceAll(loaded);

        boolean running = true;
        while (running) {
            printHeader();
            System.out.printf("Current User: %s (%s)%n%n",
                    currentUser.getName(), currentUser.getRole());

            System.out.println("Main Menu:");
            System.out.println("1. Manage Projects");
            System.out.println("2. Manage Tasks");
            System.out.println("3. View Status Reports");
            System.out.println("4. Simulate Concurrent Updates");
            System.out.println("5. Switch User");
            System.out.println("6. Save & Exit");

            int choice = ValidationUtils.readInt(scanner,
                    "Enter your choice: ", 1, 6);

            try {
                switch (choice) {
                    case 1 -> manageProjectsMenu();
                    case 2 -> manageTasksMenu();
                    case 3 -> reportService.printProjectStatusReport();
                    case 4 -> concurrencyMenu();
                    case 5 -> switchUser();
                    case 6 -> {
                        FileUtils.saveAll(projectService);
                        running = false;
                    }
                }
            } catch (RuntimeException | IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void printHeader() {
        System.out.println("\n============================");
        System.out.println("  JAVA PROJECT MANAGEMENT SYSTEM (Lab 3)");
        System.out.println("============================\n");
    }

    private void manageProjectsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nPROJECT MANAGEMENT");
            System.out.println("1. Create New Project");
            System.out.println("2. View / Filter Projects");
            System.out.println("3. View Project Details");
            System.out.println("4. Back to Main Menu");

            int ch = ValidationUtils.readInt(scanner,
                    "Enter choice: ", 1, 4);

            switch (ch) {
                case 1 -> createProjectFlow();
                case 2 -> filterProjectsFlow();
                case 3 -> viewProjectDetailsFlow();
                case 4 -> back = true;
            }
        }
    }

    private void manageTasksMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nTASK MANAGEMENT");
            System.out.println("1. Add Task");
            System.out.println("2. Update Task Status");
            System.out.println("3. Remove Task");
            System.out.println("4. Back to Main Menu");

            int ch = ValidationUtils.readInt(scanner,
                    "Enter choice: ", 1, 4);

            switch (ch) {
                case 1 -> addTaskFlow();
                case 2 -> updateTaskStatusFlow();
                case 3 -> removeTaskFlow();
                case 4 -> back = true;
            }
        }
    }

    private void addTaskFlow() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID: ");
        String name = ValidationUtils.readNonEmpty(scanner, "Enter task name: ");
        Task t = new Task(name, Status.PENDING);
        taskService.addTaskToProject(pid, t);
        System.out.println("Task added with ID: " + t.getId());
    }

    private void updateTaskStatusFlow() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID: ");
        String tid = ValidationUtils.readNonEmpty(scanner, "Enter task ID: ");

        System.out.println("1. PENDING");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. COMPLETED");

        int s = ValidationUtils.readInt(scanner, "Select status: ", 1, 3);
        Status status = Status.values()[s - 1];

        taskService.updateTaskStatus(pid, tid, status);
        System.out.println("Task updated.");
    }

    private void removeTaskFlow() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID: ");
        String tid = ValidationUtils.readNonEmpty(scanner, "Enter task ID: ");
        taskService.removeTask(pid, tid);
        System.out.println("Task removed.");
    }

    private void createProjectFlow() {
        System.out.println("\nCreate Project");
        System.out.println("1. Software Project");
        System.out.println("2. Hardware Project");

        int type = ValidationUtils.readInt(scanner,
                "Select project type: ", 1, 2);

        String name = ValidationUtils.readNonEmpty(scanner, "Enter project name: ");
        String desc = ValidationUtils.readNonEmpty(scanner, "Enter description: ");
        int teamSize = ValidationUtils.readInt(scanner, "Enter team size: ", 1, 1000);
        double budget = ValidationUtils.readDouble(scanner, "Enter budget: ", 0, Double.MAX_VALUE);

        Project p = (type == 1)
                ? new SoftwareProject(name, desc, teamSize, budget)
                : new HardwareProject(name, desc, teamSize, budget);

        projectService.addProject(p);
        System.out.println("Project created.");
    }

    private void filterProjectsFlow() {
        System.out.println("\nFilter Projects");
        System.out.println("1. View All");
        System.out.println("2. Software Only");
        System.out.println("3. Hardware Only");

        int c = ValidationUtils.readInt(scanner, "Choice: ", 1, 3);

        switch (c) {
            case 1 -> displayProjects(projectService.getAllProjects());
            case 2 -> displayProjects(projectService.filterByType("Software"));
            case 3 -> displayProjects(projectService.filterByType("Hardware"));
        }
    }

    private void displayProjects(Iterable<Project> projects) {
        for (Project p : projects) {
            System.out.println(p.summaryLine());
        }
    }

    private void viewProjectDetailsFlow() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID: ");
        Project p = projectService.findById(pid);
        p.displayProject();
        System.out.printf("Completion Rate: %.2f%%%n", p.completionPercentage());
    }

    private void switchUser() {
        System.out.println("1. Admin");
        System.out.println("2. Regular");

        int c = ValidationUtils.readInt(scanner, "Choice: ", 1, 2);
        String name = ValidationUtils.readNonEmpty(scanner, "Name: ");
        String email = ValidationUtils.readNonEmpty(scanner, "Email: ");

        currentUser = (c == 1)
                ? new AdminUser(name, email)
                : new RegularUser(name, email);
    }

    private void concurrencyMenu() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID: ");
        concurrencyService.simulateConcurrentUpdates(pid);
    }
}
