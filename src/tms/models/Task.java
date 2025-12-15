package tms.models;

import tms.interfaces.Completable;

/**
 * Task model (collections version).
 */
public class Task implements Completable {
    private static int counter = 1;

    private final String id;
    private String name;
    private Status status;
    private String assignedUserId;
    private double hoursEstimate;

    public Task(String name, Status status) {
        this.id = String.format("T%03d", counter++);
        this.name = name;
        this.status = status == null ? Status.PENDING : status;
        this.assignedUserId = null;
        this.hoursEstimate = 0.0;
    }

    // Constructor for loading with existing ID
    public Task(String id, String name, Status status) {
        this.id = id == null ? String.format("T%03d", counter++) : id;
        this.name = name;
        this.status = status == null ? Status.PENDING : status;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(String assignedUserId) { this.assignedUserId = assignedUserId; }
    public double getHoursEstimate() { return hoursEstimate; }
    public void setHoursEstimate(double hoursEstimate) { this.hoursEstimate = hoursEstimate; }

    @Override
    public boolean isCompleted() { return status == Status.COMPLETED; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", id, name, status.label());
    }
}
