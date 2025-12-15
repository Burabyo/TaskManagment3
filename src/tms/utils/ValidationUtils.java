package tms.utils;

import tms.utils.exceptions.InvalidInputException;

import java.util.Scanner;

/**
 * Input validation utilities.
 * Some methods now throw InvalidInputException for Lab 2.
 */
public class ValidationUtils {

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min || v > max) {
                    throw new InvalidInputException(String.format("Enter a number between %d and %d.", min, max));
                }
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (InvalidInputException ex) {
                System.out.println(ex.getMessage());
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
                    throw new InvalidInputException("Value out of allowed range.");
                }
                return d;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Try again (e.g., 15000).");
            } catch (InvalidInputException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static String readNonEmpty(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("Input cannot be empty.");
            } else {
                return s;
            }
        }
    }

    public static String readStatusString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.equalsIgnoreCase("pending") ||
                    s.equalsIgnoreCase("in progress") ||
                    s.equalsIgnoreCase("in_progress") ||
                    s.equalsIgnoreCase("completed")) {
                return s;
            } else {
                System.out.println("Invalid status. Choose: Pending, In Progress, Completed.");
            }
        }
    }
}
