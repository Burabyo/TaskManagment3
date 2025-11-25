
# **README**

## **Project Management System (TMS)**

This project is a simple **Task Management System** built in Java.
It allows the user to create projects, add tasks to a project, and view all tasks.
The goal of the assignment was to practice object-oriented programming (OOP), file structuring, and basic console interaction.

### **What I implemented**

* A `Task` class that stores task details such as title, description, due date, and status.
* A `Project` class that holds multiple tasks using an ArrayList.
* A `ProjectManager` service class that manages a list of projects and basic operations.
* A `Main` class with a simple text-based menu that lets a user:

    * create a project,
    * add a task to a project,
    * list projects and tasks.

### **Folder Structure**

```
src/
 └── tms/
      ├── model/
      │     ├── Task.java
      │     └── Project.java
      ├── service/
      │     └── ProjectManager.java
      └── app/
            └── Main.java
```

### **How to Run**

Compile inside the `src` directory:

```
javac tms/**/*.java
```

Run:

```
java tms.app.Main
```

---

If you want, I can also generate a **short report**, **uml diagram (text)**, or **interview preparation notes** for this project.
