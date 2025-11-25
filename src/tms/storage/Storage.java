package tms.storage;

import tms.models.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CSV-like file storage for all my tasks.
 */
public class Storage {
    private final Path filePath;

    public Storage(String filename) {
        this.filePath = Path.of(filename);
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) return tasks;
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                Task t = Task.fromCsvLine(line);
                if (t != null) tasks.add(t);
            }
        } catch (IOException e) {
            System.err.println("Failed to load tasks: " + e.getMessage());
        }
        return tasks;
    }

    public void save(List<Task> tasks) {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            for (Task t : tasks) {
                bw.write(t.toCsvLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save tasks: " + e.getMessage());
        }
    }
}
