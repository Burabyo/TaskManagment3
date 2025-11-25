package tms.app;

import tms.model.Priority;
import tms.model.Status;
import tms.model.Task;
import tms.service.TaskManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Simple console UI for the Task Management System.
 */
public class Main {
    private static final String STORAGE_FILE = "tasks.data"; // saved to project root
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager manager = new TaskManager(STORAGE_FILE);

    public static void main(String[] args) {
        System.out.println("Welcome to Task Manager");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": createTask(); break;
                case "2": listTasks(manager.listAll()); break;
                case "3": updateTask(); break;
                case "4": deleteTask(); break;
                case "5": markDone(); break;
                case "6": search(); break;
                case "7": sortMenu(); break;
                case "0": running = false; break;
                default: System.out.println("Unknown option");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add task");
        System.out.println("2. List all tasks");
        System.out.println("3. Update task");
        System.out.println("4. Delete task");
        System.out.println("5. Mark task done");
        System.out.println("6. Search tasks");
        System.out.println("7. Sort tasks");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private static void createTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Due date (YYYY-MM-DD) or leave empty: ");
        String dd = scanner.nextLine().trim();
        LocalDate due = null;
        if (!dd.isEmpty()) {
            try { due = LocalDate.parse(dd); } catch (DateTimeParseException e) { System.out.println("Invalid date format, skipping due date."); }
        }
        System.out.print("Priority (LOW/MEDIUM/HIGH) [MEDIUM]: ");
        String p = scanner.nextLine().trim();
        Priority pr = Priority.fromString(p);
        Task t = manager.addTask(title, desc, due, pr);
        System.out.println("Created: " + t);
    }

    private static void listTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private static void updateTask() {
        System.out.print("Enter task id to update: ");
        int id = readInt();
        Task t = manager.findById(id);
        if (t == null) { System.out.println("Not found"); return; }

        System.out.println("Leave empty to keep existing.");
        System.out.print("New title [" + t.getTitle() + "]: ");
        String title = scanner.nextLine();
        if (title.isEmpty()) title = null;

        System.out.print("New description [" + t.getDescription() + "]: ");
        String desc = scanner.nextLine();
        if (desc.isEmpty()) desc = null;

        System.out.print("New due date (YYYY-MM-DD) or leave empty [" + (t.getDueDate()==null?"":t.getDueDate()) + "]: ");
        String dd = scanner.nextLine().trim();
        LocalDate due = null;
        if (!dd.isEmpty()) {
            try { due = LocalDate.parse(dd); } catch (DateTimeParseException e) { System.out.println("Invalid date format, skipping due date change."); }
        }

        System.out.print("Priority (LOW/MEDIUM/HIGH) [" + t.getPriority() + "]: ");
        String p = scanner.nextLine().trim();
        Priority pr = p.isEmpty() ? null : Priority.fromString(p);

        System.out.print("Status (TODO/IN_PROGRESS/DONE) [" + t.getStatus() + "]: ");
        String s = scanner.nextLine().trim();
        Status st = s.isEmpty() ? null : Status.fromString(s);

        boolean ok = manager.updateTask(id, title, desc, due, pr, st);
        System.out.println(ok ? "Updated" : "Update failed");
    }

    private static void deleteTask() {
        System.out.print("Enter task id to delete: ");
        int id = readInt();
        boolean ok = manager.deleteTask(id);
        System.out.println(ok ? "Deleted" : "Not found");
    }

    private static void markDone() {
        System.out.print("Enter task id to mark done: ");
        int id = readInt();
        boolean ok = manager.markDone(id);
        System.out.println(ok ? "Marked done" : "Not found");
    }

    private static void search() {
        System.out.print("Enter search keyword: ");
        String q = scanner.nextLine();
        List<Task> found = manager.search(q);
        listTasks(found);
    }

    private static void sortMenu() {
        System.out.println("Sort by:");
        System.out.println("1. Due date ascending");
        System.out.println("2. Due date descending");
        System.out.println("3. Priority ascending");
        System.out.println("4. Priority descending");
        System.out.print("Choice: ");
        String c = scanner.nextLine();
        switch (c) {
            case "1": listTasks(manager.sortByDueDate(true)); break;
            case "2": listTasks(manager.sortByDueDate(false)); break;
            case "3": listTasks(manager.sortByPriority(true)); break;
            case "4": listTasks(manager.sortByPriority(false)); break;
            default: System.out.println("Unknown");
        }
    }

    private static int readInt() {
        try {
            String s = scanner.nextLine().trim();
            return Integer.parseInt(s);
        } catch (Exception e) {
            return -1;
        }
    }
}
