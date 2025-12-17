package tms.utils;

import tms.utils.exceptions.InvalidInputException;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Input helpers and Regex validation utilities.
 */
public class ValidationUtils {

    private static final Pattern PROJECT_ID = Pattern.compile("^P\\d{3}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern TASK_ID = Pattern.compile("^T\\d{3}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern EMAIL = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min || v > max) throw new InvalidInputException("Enter a number between " + min + " and " + max);
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number, try again.");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double readDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double v = Double.parseDouble(line);
                if (v < min || v > max) throw new InvalidInputException("Number must be between " + min + " and " + max);
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number, try again.");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String readNonEmpty(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.isEmpty()) System.out.println("Input cannot be empty.");
            else return s;
        }
    }

    public static String readStatusString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.equalsIgnoreCase("pending") || s.equalsIgnoreCase("in progress") ||
                    s.equalsIgnoreCase("in_progress") || s.equalsIgnoreCase("completed")) {
                return s;
            } else {
                System.out.println("Invalid status. Choose: Pending, In Progress, Completed.");
            }
        }
    }

    public static boolean isValidProjectId(String id) {
        return id != null && PROJECT_ID.matcher(id.trim()).matches();
    }

    public static boolean isValidTaskId(String id) {
        return id != null && TASK_ID.matcher(id.trim()).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email.trim()).matches();
    }
}
