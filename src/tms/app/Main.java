package tms.app;

import tms.model.Project;
import tms.model.Task;
import tms.service.ProjectManager;

import java.util.Scanner;

/**
 * The interface of my project
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ProjectManager projectManager = new ProjectManager();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n--- PROJECT MANAGEMENT SYSTEM ---");
            System.out.println("1. Create Project");
            System.out.println("2. View Projects");
            System.out.println("3. Add Task to Project");
            System.out.println("4. Update Task Status");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    createProject();
                    break;

                case 2:
                    viewProjects();
                    break;

                case 3:
                    addTask();
                    break;

                case 4:
                    updateTask();
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }

    // Create project
    private static void createProject() {
        System.out.print("Enter project name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String desc = scanner.nextLine();

        Project p = new Project(name, desc);

        if (projectManager.addProject(p)) {
            System.out.println("Project created with ID: " + p.getId());
        } else {
            System.out.println("Project list full!");
        }
    }

    // List all projects
    private static void viewProjects() {
        Project[] list = projectManager.getAllProjects();

        if (list.length == 0) {
            System.out.println("No projects found.");
            return;
        }

        System.out.println("\n--- PROJECTS ---");
        for (Project p : list) {
            System.out.println(p.getId() + " | " + p.getName() +
                    " | Tasks: " + p.getTaskCount() +
                    " | Completion: " + p.getCompletionRate() + "%");
        }
    }

    // Add task to project
    private static void addTask() {
        System.out.print("Enter project ID: ");
        String pid = scanner.nextLine();

        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter status (Pending/In Progress/Completed): ");
        String status = scanner.nextLine();

        Task t = new Task(name, status);

        if (projectManager.addTaskToProject(pid, t)) {
            System.out.println("Task added with ID: " + t.getId());
        } else {
            System.out.println("Failed. Invalid project or full task list.");
        }
    }

    // Update a task inside a project
    private static void updateTask() {
        System.out.print("Enter project ID: ");
        String pid = scanner.nextLine();

        Project p = projectManager.getProjectById(pid);
        if (p == null) {
            System.out.println("Project not found.");
            return;
        }

        System.out.print("Enter task ID: ");
        String tid = scanner.nextLine();

        Task task = p.getTaskById(tid);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        System.out.print("Enter new status: ");
        String newStatus = scanner.nextLine();

        task.setStatus(newStatus);
        System.out.println("Task updated!");
    }
}
