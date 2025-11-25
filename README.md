# Project/Task Management System (Java)

## Summary
Console-based Project/Task Management System implemented in Java 21.  
Stores data in memory using arrays. Implements project types, tasks, user roles, and status reporting.

## Implemented
- Abstract `Project` with `SoftwareProject` and `HardwareProject`.
- `Task` with auto-generated IDs and `Status` enum.
- `User` with `AdminUser` and `RegularUser`.
- `ProjectService`, `TaskService`, `ReportService` (arrays only).
- Menu-driven console UI (`ConsoleMenu`) with input validation.
- Budget and team size included for each project.
- Completion percentage shown with two decimals.

## How to run
1. Compile all `.java` files under `src`.
2. From project root:
   ```bash
   javac -d out src/tms/**/*.java
   java -cp out tms.app.Main
