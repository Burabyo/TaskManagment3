package tms.service;

import tms.models.Project;
import tms.models.Task;
import tms.models.Status;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple concurrency helper that spawns threads to update tasks.
 */
public class ConcurrencyService {
    private final ProjectService projectService;

    public ConcurrencyService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Simulate multiple threads marking tasks as completed for a project.
     */
    public void simulateConcurrentUpdates(String projectId) {
        Project p = projectService.findById(projectId);
        List<Task> tasks = p.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to run concurrency simulation.");
            return;
        }

        ExecutorService ex = Executors.newFixedThreadPool(Math.min(4, tasks.size()));
        for (Task t : tasks) {
            ex.submit(() -> {
                try {
                    // pretend work
                    Thread.sleep((long)(Math.random() * 500));
                    synchronized (t) {
                        t.setStatus(Status.COMPLETED);
                        System.out.printf("Thread %s marked %s as Completed%n", Thread.currentThread().getName(), t.getId());
                    }
                } catch (InterruptedException ignored) { }
            });
        }
        ex.shutdown();
        while (!ex.isTerminated()) { /* wait */ }
        System.out.println("Concurrency simulation complete.");
    }
}
