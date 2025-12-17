package tms.models;

import java.util.UUID;

public class Task {

    private final String id;
    private String name;
    private Status status;

    // ✅ REQUIRED BY ConsoleMenu
    private String assignedUserId;

    // Optional but already referenced earlier
    private double hoursEstimate;

    /** Normal constructor */
    public Task(String name, Status status) {
        this.id = "TSK" + UUID.randomUUID().toString().substring(0, 4);
        this.name = name;
        this.status = status;
    }

    /** Used by FileUtils when loading from storage */
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

    /** ✅ FIX: method expected by ConsoleMenu */
    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public double getHoursEstimate() {
        return hoursEstimate;
    }

    public void setHoursEstimate(double hoursEstimate) {
        this.hoursEstimate = hoursEstimate;
    }
}
