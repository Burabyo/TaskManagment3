package tms.utils;

import java.util.Scanner;

/**
 * Small utilities for reading and validating console input.
 * Keeps the menu code clean.
 */
public class ValidationUtils {

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min || v > max) {
                    System.out.printf("Enter a number between %d and %d.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static double readDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double d = Double.parseDouble(line);
                if (d < min || d > max) {
                    System.out.println("Value out of allowed range.");
                    continue;
                }
                return d;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Try again (e.g., 15000).");
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

    // Read a status from user; returns null if invalid
    public static String readStatusString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.equalsIgnoreCase("pending") || s.equalsIgnoreCase("in progress") || s.equalsIgnoreCase("completed")) {
                return s;
            } else {
                System.out.println("Invalid status. Choose: Pending, In Progress, Completed.");
            }
        }
    }
}
