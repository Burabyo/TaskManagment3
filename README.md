Got you 👍
I’ll give you a **clean, human-sounding, detailed README** that:

* Explains **what the system does**, not “Lab 1 / Lab 2 / Lab 3”
* Describes **features progressively**, like a real project
* Mentions **new capabilities** (collections, streams, persistence, concurrency, testing)
* Has **no icons, no AI tone, no course-style wording**
* Fits an **interview + GitHub submission**

You can paste this directly as `README.md`.

---

# Project / Task Management System (Java)

## Overview

This is a console-based Project and Task Management System implemented in Java.
The application allows users to create and manage projects, assign and track tasks, monitor completion progress, and generate summary reports. The system is designed using object-oriented principles and follows a layered structure to separate concerns between data models, business logic, utilities, and user interaction.

The application runs entirely in the console and does not rely on external frameworks or databases.


## Core Functionality

### Project Management

* Supports multiple project types through inheritance.
* Each project has:

    * A unique auto-generated ID
    * Name and description
    * Team size and budget
    * A configurable maximum number of tasks
* Projects track their own tasks and compute completion statistics.

### Task Management

* Tasks belong to a specific project.
* Each task has:

    * Auto-generated ID
    * Name
    * Status (Pending, In Progress, Completed)
    * Optional assigned user
    * Optional estimated hours
* Task status updates automatically affect project progress.

### Users and Roles

* The system supports different user roles:

    * Admin users can perform restricted actions such as deleting tasks.
    * Regular users can view and update tasks but have limited permissions.
* User switching is supported at runtime.


## Application Structure

The project is organized into clear packages, each with a specific responsibility.

### `tms.models`

Contains the core domain objects:

* `Project` (abstract base class)
* `SoftwareProject` and `HardwareProject`
* `Task`
* `Status` enum
* `User`, `AdminUser`, and `RegularUser`

These classes represent real-world entities and contain only data and core behavior.


### `tms.service`

Contains business logic and coordination between models:

* `ProjectService` manages all projects using a map for fast lookup.
* `TaskService` handles task-related operations within projects.
* `ReportService` generates read-only summaries and progress reports.
* `ConcurrencyService` simulates concurrent updates to demonstrate multi-threading concepts.

This layer ensures that all application rules are enforced consistently.


### `tms.utils`

Provides supporting utilities:

* `ValidationUtils` centralizes input validation and formatting rules.
* `FileUtils` handles saving and loading project data to and from files.
* `ConsoleMenu` manages user interaction and delegates logic to services.


### `tms.utils.exceptions`

Defines custom exceptions to represent application-specific errors such as:

* Missing projects or tasks
* Invalid data
* Unauthorized operations

Using custom exceptions improves readability and keeps error handling explicit.


## Data Handling and Processing

* Projects are stored in memory using Java collections.
* Streams are used for filtering, searching, and calculating statistics such as completion percentage.
* Project data can be saved to disk and restored on application startup.
* Reporting features compute aggregate progress across all projects.


## Concurrency Support

The system includes a concurrency simulation that runs multiple threads updating project tasks simultaneously.
This feature demonstrates awareness of real-world scenarios where multiple users or processes may interact with shared data.


## Testing

The project includes unit tests written using JUnit 5.
Tests focus on:

* Task completion calculations
* Project progress percentage
* Task status updates
* Correct exception handling for invalid operations

The design preserves backward compatibility so that existing tests continue to pass as new features are added.


## Design Principles

* Object-Oriented Programming (inheritance, polymorphism, encapsulation)
* Separation of concerns through layered architecture
* Use of enums for controlled state
* Custom exceptions for clear error handling
* Extensible design that allows new project types or features to be added easily


## How to Run

1. Ensure Java 21 (or compatible) is installed.
2. Compile all source files under the `src` directory.
3. Run the main application class.

Example:

```bash
javac -d out src/tms/**/*.java
java -cp out tms.app.Main
```

## Summary

This project demonstrates how a simple console application can be structured into a maintainable and extensible Java system. It combines core Java concepts such as collections, streams, file handling, concurrency, testing, and clean object-oriented design into a cohesive application suitable for academic and interview evaluation.

## Class Diagram
**[View Class Diagram](docs/ClassDiaram.pdf)**
