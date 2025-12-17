package tms.models;

import java.util.UUID;

public class Task {

    private final String id;        // unique task identifier
    private String name;
    private Status status;

    private String assignedUserId;  // tracks which user is assigned
    private double hoursEstimate;   // optional estimated hours

    /** Constructor for creating a new task */
    public Task(String name, Status status) {
        this.id = "TSK" + UUID.randomUUID().toString().substring(0, 4); // short unique ID
        this.name = name;
        this.status = status;
    }

    /** Constructor used when loading task from storage */
    public Task(String id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    /* ---------------- GETTERS / SETTERS ---------------- */

    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(String assignedUserId) { this.assignedUserId = assignedUserId; }

    public double getHoursEstimate() { return hoursEstimate; }
    public void setHoursEstimate(double hoursEstimate) { this.hoursEstimate = hoursEstimate; }
}
