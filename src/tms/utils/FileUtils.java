package tms.utils;

import tms.models.*;
import tms.service.ProjectService;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;


public class FileUtils {

    private static final Path STORAGE = Paths.get("projects_data.txt");

    // Save all projects to file (overwrite)
    public static void saveAll(ProjectService ps) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Project p : ps.getAllProjects()) {
            String type = p.getProjectDetails();
            String base = String.format("{\"id\":\"%s\",\"type\":\"%s\",\"name\":\"%s\",\"desc\":\"%s\",\"team\":%d,\"budget\":%s,\"tasks\":[%s]}",
                    p.getId(), type, escape(p.getName()), escape(p.getDescription()), p.getTeamSize(),
                    Double.toString(p.getBudget()), p.tasksToSaveString());
            lines.add(base);
        }
        Files.write(STORAGE, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // Load projects, create list (basic parser)
    public static List<Project> loadAll() {
        if (!Files.exists(STORAGE)) return Collections.emptyList();
        try {
            List<String> lines = Files.readAllLines(STORAGE);
            List<Project> out = new ArrayList<>();
            for (String ln : lines) {
                Map<String, String> map = parseTopLevel(ln);
                String type = map.get("type");
                String name = unescape(map.get("name"));
                String desc = unescape(map.get("desc"));
                int team = Integer.parseInt(map.getOrDefault("team","0"));
                double budget = Double.parseDouble(map.getOrDefault("budget","0"));
                Project p = type.equalsIgnoreCase("Hardware")
                        ? new HardwareProject(name, desc, team, budget)
                        : new SoftwareProject(name, desc, team, budget);
                // tasks parsing
                String tasksPart = extractTasksArray(ln);
                if (tasksPart != null && !tasksPart.isBlank()) {
                    String[] toks = tasksPart.split("\\},\\{");
                    for (String tkn : toks) {
                        String cleaned = tkn.replaceAll("^\\{","").replaceAll("\\}$","");
                        Map<String,String> tm = parseTopLevel("{" + cleaned + "}");
                        String tid = tm.get("id");
                        String tname = unescape(tm.get("name"));
                        String status = tm.get("status");
                        Task task = new Task(tid, tname, Status.fromString(status));
                        p.addTask(task);
                    }
                }
                out.add(p);
            }
            return out;
        } catch (Exception e) {
            System.out.println("Failed to load data: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private static String extractTasksArray(String ln) {
        int start = ln.indexOf("\"tasks\":[");
        if (start < 0) return null;
        int s = ln.indexOf('[', start);
        int e = ln.lastIndexOf(']');
        if (s < 0 || e < 0 || e <= s) return null;
        return ln.substring(s+1, e);
    }

    private static Map<String,String> parseTopLevel(String json) {
        Map<String,String> map = new HashMap<>();
        // Very small parser: find "key":"value" or "key":value
        String inside = json.trim();
        if (inside.startsWith("{")) inside = inside.substring(1);
        if (inside.endsWith("}")) inside = inside.substring(0, inside.length()-1);
        String[] parts = inside.split("\",\"");
        for (String p : parts) {
            String[] kv = p.split("\":");
            if (kv.length < 2) continue;
            String k = kv[0].replaceAll("^\"|\"$","").replaceAll("^,","").replaceAll("^\"","");
            String v = kv[1].trim();
            v = v.replaceAll("^\"","").replaceAll("\"$","");
            // remove trailing commas
            if (v.endsWith(",")) v = v.substring(0, v.length()-1);
            map.put(k, v);
        }
        return map;
    }

    private static String escape(String s) { return s == null ? "" : s.replace("\"","\\\""); }
    private static String unescape(String s) { return s == null ? "" : s.replace("\\\"", "\""); }
}
