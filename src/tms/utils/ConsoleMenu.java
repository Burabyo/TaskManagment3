package tms.utils;

import tms.models.*;
import tms.service.*;

import java.util.Scanner;

/**
 * ConsoleMenu: menu-driven UI that coordinates services and models.
 * - Handles role-based access (Admin/Regular) for delete/update.
 * - Provides project management, task management, reports, user switching.
 *
 * Medium complexity: clear but not over-engineered.
 */
public class ConsoleMenu {

    private final Scanner scanner;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ReportService reportService;
    private User currentUser;

    public ConsoleMenu(Scanner scanner, ProjectService projectService, TaskService taskService, ReportService reportService, User initialUser) {
        this.scanner = scanner;
        this.projectService = projectService;
        this.taskService = taskService;
        this.reportService = reportService;
        this.currentUser = initialUser;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printHeader();
            System.out.printf("Current User: %s (%s)%n%n", currentUser.getName(), currentUser.getRole());
            System.out.println("Main Menu:");
            System.out.println("1. Manage Projects");
            System.out.println("2. Manage Tasks");
            System.out.println("3. View Status Reports");
            System.out.println("4. Switch User");
            System.out.println("5. Exit");
            int choice = ValidationUtils.readInt(scanner, "Enter your choice: ", 1, 5);
            switch (choice) {
                case 1 -> manageProjectsMenu();
                case 2 -> manageTasksMenu();
                case 3 -> reportService.printProjectStatusReport();
                case 4 -> switchUser();
                case 5 -> running = false;
            }
        }
    }

    private void printHeader() {
        System.out.println("\n============================");
        System.out.println("  JAVA PROJECT MANAGEMENT SYSTEM");
        System.out.println("============================\n");
    }

    // ----------------- Project Menu -----------------
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
        System.out.println("\nCreate Project:");
        System.out.println("1. Software Project");
        System.out.println("2. Hardware Project");
        int type = ValidationUtils.readInt(scanner, "Select type: ", 1, 2);
        String name = ValidationUtils.readNonEmpty(scanner, "Enter project name: ");
        String desc = ValidationUtils.readNonEmpty(scanner, "Enter description: ");
        int teamSize = ValidationUtils.readInt(scanner, "Enter team size: ", 1, 1000);
        double budget = ValidationUtils.readDouble(scanner, "Enter budget (e.g., 15000): ", 0, Double.MAX_VALUE);

        Project p;
        if (type == 1) p = new SoftwareProject(name, desc, teamSize, budget, 50);
        else p = new HardwareProject(name, desc, teamSize, budget, 50);

        boolean ok = projectService.addProject(p);
        if (ok) System.out.printf("Project created with ID %s.%n", p.getId());
        else System.out.println("Failed to create project: storage full.");
    }

    private void filterProjectsFlow() {
        System.out.println("\nFilter Options:");
        System.out.println("1. View All Projects");
        System.out.println("2. Software Projects Only");
        System.out.println("3. Hardware Projects Only");
        System.out.println("4. Search by Budget Range");
        int choice = ValidationUtils.readInt(scanner, "Enter filter choice: ", 1, 4);
        switch (choice) {
            case 1 -> displayProjects(projectService.getAllProjects());
            case 2 -> displayProjects(projectService.filterByType("Software"));
            case 3 -> displayProjects(projectService.filterByType("Hardware"));
            case 4 -> {
                double min = ValidationUtils.readDouble(scanner, "Enter min budget: ", 0, Double.MAX_VALUE);
                double max = ValidationUtils.readDouble(scanner, "Enter max budget: ", 0, Double.MAX_VALUE);
                if (max < min) System.out.println("Invalid range: max < min.");
                else displayProjects(projectService.searchByBudgetRange(min, max));
            }
        }
    }

    private void displayProjects(Project[] projects) {
        if (projects.length == 0) { System.out.println("No projects found."); return; }
        System.out.println("\nPROJECT LIST:");
        for (Project p : projects) {
            System.out.println(p.summaryLine());
        }
    }

    private void viewProjectDetailsFlow() {
        String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID (or 0 to return): ");
        if ("0".equals(pid)) return;
        Project p = projectService.findById(pid);
        if (p == null) { System.out.println("Project not found."); return; }
        System.out.println();
        p.displayProject();
        System.out.println("\nAssociated Tasks:");
        Task[] tasks = p.getTasks();
        if (tasks.length == 0) System.out.println("No tasks yet.");
        else {
            System.out.printf("%-8s %-25s %-12s %-10s %-6s%n", "TASKID", "NAME", "STATUS", "ASSIGNED", "HOURS");
            for (Task t : tasks) {
                System.out.printf("%-8s %-25s %-12s %-10s %-6.1f%n",
                        t.getId(), t.getName(), t.getStatus().label(), t.getAssignedUserId()==null?"-":t.getAssignedUserId(), t.getHoursEstimate());
            }
        }
        System.out.printf("Completion Rate: %.2f%%%n", p.completionPercentage());
        // Options
        System.out.println("\nOptions:");
        System.out.println("1. Add New Task");
        System.out.println("2. Update Task Status");
        System.out.println("3. Remove Task");
        System.out.println("4. Back");
        int ch = ValidationUtils.readInt(scanner, "Enter your choice: ", 1, 4);
        switch (ch) {
            case 1 -> addTaskToProjectFlow(p);
            case 2 -> updateTaskStatusFlow(p);
            case 3 -> removeTaskFlow(p);
            case 4 -> { /* back */ }
        }
    }

    // ----------------- Task Menu -----------------
    private void manageTasksMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nTASK MANAGEMENT");
            System.out.println("1. Add Task to Project");
            System.out.println("2. View Tasks for Project");
            System.out.println("3. Update Task Status");
            System.out.println("4. Delete Task");
            System.out.println("5. Back to Main Menu");
            int c = ValidationUtils.readInt(scanner, "Enter choice: ", 1, 5);
            switch (c) {
                case 1 -> {
                    String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID to add task: ");
                    Project p = projectService.findById(pid);
                    if (p == null) System.out.println("Project not found.");
                    else addTaskToProjectFlow(p);
                }
                case 2 -> {
                    String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID to view tasks: ");
                    Project p = projectService.findById(pid);
                    if (p == null) System.out.println("Project not found.");
                    else {
                        Task[] tasks = p.getTasks();
                        if (tasks.length == 0) System.out.println("No tasks.");
                        else {
                            System.out.printf("%-8s %-25s %-12s %-10s %-6s%n", "TASKID", "NAME", "STATUS", "ASSIGNED", "HOURS");
                            for (Task t : tasks) {
                                System.out.printf("%-8s %-25s %-12s %-10s %-6.1f%n",
                                        t.getId(), t.getName(), t.getStatus().label(), t.getAssignedUserId()==null?"-":t.getAssignedUserId(), t.getHoursEstimate());
                            }
                        }
                    }
                }
                case 3 -> {
                    String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID that contains the task: ");
                    Project p = projectService.findById(pid);
                    if (p == null) System.out.println("Project not found.");
                    else updateTaskStatusFlow(p);
                }
                case 4 -> {
                    String pid = ValidationUtils.readNonEmpty(scanner, "Enter project ID that contains the task: ");
                    Project p = projectService.findById(pid);
                    if (p == null) System.out.println("Project not found.");
                    else removeTaskFlow(p);
                }
                case 5 -> back = true;
            }
        }
    }

    private void addTaskToProjectFlow(Project p) {
        String name = ValidationUtils.readNonEmpty(scanner, "Enter task name: ");
        if (p.getTasks() != null) {
            // Project.addTask prevents duplicate names; we can still check
            for (Task existing : p.getTasks()) {
                if (existing.getName().equalsIgnoreCase(name)) {
                    System.out.println("Duplicate task name. Aborting.");
                    return;
                }
            }
        }
        String statusStr = ValidationUtils.readStatusString(scanner, "Enter initial status (Pending/In Progress/Completed): ");
        Status st = Status.fromString(statusStr);
        String assigned = ValidationUtils.readNonEmpty(scanner, "Enter assigned user ID (or - for none): ");
        if ("-".equals(assigned)) assigned = null;
        double hours = ValidationUtils.readDouble(scanner, "Enter hours estimate (0 if none): ", 0, 10000);

        Task t = new Task(name, st, assigned, hours);
        boolean ok = taskService.addTaskToProject(p.getId(), t);
        if (ok) System.out.printf("Task \"%s\" added successfully to Project %s!%n", name, p.getId());
        else System.out.println("Failed to add task (maybe project task storage is full or duplicate).");
    }

    private void updateTaskStatusFlow(Project p) {
        Task[] tasks = p.getTasks();
        if (tasks.length == 0) { System.out.println("No tasks to update."); return; }
        System.out.print("Enter task ID: ");
        String tid = scanner.nextLine().trim();
        Task t = p.findTaskById(tid);
        if (t == null) { System.out.println("Task not found."); return; }
        String statusStr = ValidationUtils.readStatusString(scanner, "Enter new status: ");
        Status ns = Status.fromString(statusStr);
        t.setStatus(ns);
        System.out.printf("Task \"%s\" marked as %s.%n", t.getName(), ns.label());
    }

    private void removeTaskFlow(Project p) {
        if (!(currentUser instanceof AdminUser)) {
            System.out.println("Only Admin can delete tasks.");
            return;
        }
        Task[] tasks = p.getTasks();
        if (tasks.length == 0) { System.out.println("No tasks to remove."); return; }
        System.out.print("Enter task ID to remove: ");
        String tid = scanner.nextLine().trim();
        boolean ok = taskService.removeTask(p.getId(), tid);
        System.out.println(ok ? "Task removed." : "Task not found.");
    }

    // -------------- Other helpers ---------------
    private void switchUser() {
        System.out.println("\nSwitch User:");
        System.out.println("1. Admin User");
        System.out.println("2. Regular User");
        int choice = ValidationUtils.readInt(scanner, "Choose user type: ", 1, 2);
        String name = ValidationUtils.readNonEmpty(scanner, "Enter name: ");
        String email = ValidationUtils.readNonEmpty(scanner, "Enter email: ");
        if (choice == 1) currentUser = new AdminUser(name, email);
        else currentUser = new RegularUser(name, email);
        System.out.printf("Switched to %s (%s).%n", currentUser.getName(), currentUser.getRole());
    }

    // Entry method to expose Task menu from main
    public void openTaskManagement() {
        manageTasksMenu();
    }
}
