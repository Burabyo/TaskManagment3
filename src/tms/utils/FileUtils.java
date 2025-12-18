package tms.utils;

import tms.models.*;
import tms.service.ProjectService;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileUtils {

    private static final Path STORAGE =
            Paths.get(System.getProperty("user.dir"), "projects_data.txt");

    public static void saveAll(ProjectService ps) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Project p : ps.getAllProjects()) {
            lines.add(String.join("|",
                    "PROJECT",
                    p.getId(),
                    p.getProjectDetails(),
                    p.getName(),
                    p.getDescription(),
                    String.valueOf(p.getTeamSize()),
                    String.valueOf(p.getBudget())
            ));

            for (Task t : p.getTasks()) {
                lines.add(String.join("|",
                        "TASK",
                        p.getId(),
                        t.getId(),
                        t.getName(),
                        t.getStatus().name()
                ));
            }
        }

        Files.write(STORAGE, lines,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static List<Project> loadAll() {
        if (!Files.exists(STORAGE)) {
            return Collections.emptyList();
        }

        Map<String, Project> projects = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(STORAGE);

            for (String line : lines) {
                String[] parts = line.split("\\|");

                if (parts[0].equals("PROJECT")) {
                    String id = parts[1];
                    String type = parts[2];
                    String name = parts[3];
                    String desc = parts[4];
                    int team = Integer.parseInt(parts[5]);
                    double budget = Double.parseDouble(parts[6]);

                    Project p = type.equalsIgnoreCase("Hardware")
                            ? new HardwareProject(id, name, desc, team, budget)
                            : new SoftwareProject(id, name, desc, team, budget);

                    projects.put(id, p);
                }

                if (parts[0].equals("TASK")) {
                    String projectId = parts[1];
                    String taskId = parts[2];
                    String taskName = parts[3];
                    Status status = Status.valueOf(parts[4]);

                    Project p = projects.get(projectId);
                    if (p != null) {
                        Task t = new Task(taskId, taskName, status);
                        p.addTask(t);
                    }
                }
            }

            return new ArrayList<>(projects.values());

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
