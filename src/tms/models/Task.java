package tms.models;

import tms.interfaces.Completable;

/**
 * Task model.
 *
 * - Auto-generates unique task IDs (TSK001, TSK002, ...).
 * - Stores name, assigned user id (optional), status and hours estimate.
 * - Implements Completable for easy checking of completion.
 */
public class Task implements Completable {
    private static int counter = 1;

    private final String id;
    private String name;
    private String assignedUserId; // can be null
    private Status status;
    private double hoursEstimate; // optional, 0 if not provided

    public Task(String name, Status status) {
        this.id = String.format("TSK%03d", counter++);
        this.name = name;
        this.status = status == null ? Status.PENDING : status;
        this.assignedUserId = null;
        this.hoursEstimate = 0.0;
    }

    // Additional constructor with assigned user and hours
    public Task(String name, Status status, String assignedUserId, double hoursEstimate) {
        this.id = String.format("TSK%03d", counter++);
        this.name = name;
        this.status = status == null ? Status.PENDING : status;
        this.assignedUserId = assignedUserId;
        this.hoursEstimate = hoursEstimate;
    }

    // Getters and setters
    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(String assignedUserId) { this.assignedUserId = assignedUserId; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public double getHoursEstimate() { return hoursEstimate; }
    public void setHoursEstimate(double hoursEstimate) { this.hoursEstimate = hoursEstimate; }

    @Override
    public boolean isCompleted() {
        return status == Status.COMPLETED;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | Assigned:%s | Hours:%.1f",
                id, name, status.label(), assignedUserId == null ? "-" : assignedUserId, hoursEstimate);
    }
}
