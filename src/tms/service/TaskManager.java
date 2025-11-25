package tms.service;

import tms.model.Priority;
import tms.model.Status;
import tms.model.Task;
import tms.storage.Storage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * In-memory manager for tasks; persists using Storage.
 */
public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private final Storage storage;
    private int nextId = 1;

    public TaskManager(String storageFile) {
        storage = new Storage(storageFile);
        load();
    }

    private void load() {
        List<Task> loaded = storage.load();
        tasks.addAll(loaded);
        // set nextId to max + 1
        int max = tasks.stream().mapToInt(Task::getId).max().orElse(0);
        nextId = max + 1;
    }

    public void save() {
        storage.save(tasks);
    }

    public Task addTask(String title, String desc, LocalDate dueDate, Priority priority) {
        Task t = new Task(nextId++, title, desc, dueDate, priority, Status.TODO);
        tasks.add(t);
        save();
        return t;
    }

    public boolean updateTask(int id, String newTitle, String newDesc, LocalDate newDue, Priority newPriority, Status newStatus) {
        Task t = findById(id);
        if (t == null) return false;
        if (newTitle != null) t.setTitle(newTitle);
        if (newDesc != null) t.setDescription(newDesc);
        if (newDue != null) t.setDueDate(newDue);
        if (newPriority != null) t.setPriority(newPriority);
        if (newStatus != null) t.setStatus(newStatus);
        save();
        return true;
    }

    public boolean deleteTask(int id) {
        Task t = findById(id);
        if (t == null) return false;
        tasks.remove(t);
        save();
        return true;
    }

    public boolean markDone(int id) {
        return updateTask(id, null, null, null, null, Status.DONE);
    }

    public Task findById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public List<Task> listAll() {
        return new ArrayList<>(tasks);
    }

    public List<Task> listByStatus(Status status) {
        return tasks.stream().filter(t -> t.getStatus() == status).collect(Collectors.toList());
    }

    public List<Task> search(String keyword) {
        String q = keyword == null ? "" : keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(q) || t.getDescription().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Task> sortByDueDate(boolean asc) {
        Comparator<Task> cmp = Comparator.comparing(t -> (t.getDueDate() == null ? LocalDate.MAX : t.getDueDate()));
        if (!asc) cmp = cmp.reversed();
        return tasks.stream().sorted(cmp).collect(Collectors.toList());
    }

    public List<Task> sortByPriority(boolean asc) {
        Comparator<Task> cmp = Comparator.comparing(Task::getPriority);
        if (!asc) cmp = cmp.reversed();
        return tasks.stream().sorted(cmp).collect(Collectors.toList());
    }
}
