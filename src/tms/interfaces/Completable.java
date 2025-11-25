package tms.interfaces;

/**
 * Simple interface that exposes completion-check.
 * Used by Task so projects can count completed tasks.
 */
public interface Completable {
    boolean isCompleted();
}
