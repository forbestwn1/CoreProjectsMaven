package com.nosliw.datasource.utility.toronto.community;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Main2_getAllCommunitys {

    private static final Path SOURCE_DIR = Paths.get("C:\\temp\\export\\alllocations");
    private static final Path TARGET_DIR = Paths.get("C:\\temp\\export\\allcommunitycenter");

    public static void main(String[] args) {
        try {
            if (!Files.exists(SOURCE_DIR)) {
                System.err.println("Source folder does not exist: " + SOURCE_DIR.toAbsolutePath());
                return;
            }

            Files.createDirectories(TARGET_DIR);

            int copiedCount = 0;
            try (var paths = Files.list(SOURCE_DIR)) {
                var files = paths.filter(Files::isRegularFile)
                        .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".json"))
                        .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                        .toList();

                for (Path sourceFile : files) {
                    String title = null;
                    String regLink = null;
                    boolean validHours = false;

                    try {
                        String jsonText = Files.readString(sourceFile, StandardCharsets.UTF_8);
                        Object parsed = new JSONTokener(jsonText).nextValue();
                        title = findFirstValue(parsed, "title");
                        regLink = findFirstValue(parsed, "regLink");
                        validHours = hasUsableHours(parsed);
                    } catch (Exception e) {
                        continue;
                    }

                    if (title == null || title.trim().isEmpty() || regLink == null || regLink.trim().isEmpty() || !validHours) {
                        continue;
                    }

                    String safeTitle = sanitizeFileName(title.trim());
                    String fileName = safeTitle + "_" + sourceFile.getFileName().toString();
                    Path targetFile = TARGET_DIR.resolve(fileName);
                    Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    copiedCount++;
                    System.out.println("Copied: " + sourceFile.getFileName() + " -> " + targetFile.getFileName());
                }
            }

            System.out.println("Completed. Copied " + copiedCount + " files to " + TARGET_DIR.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findFirstValue(Object value, String targetKey) {
        if (value == null) {
            return null;
        }

        if (value instanceof JSONObject jsonObject) {
            if (jsonObject.has(targetKey)) {
                Object rawValue = jsonObject.get(targetKey);
                if (rawValue == null) {
                    return null;
                }
                String text = rawValue.toString();
                return text == null || text.trim().isEmpty() ? null : text;
            }

            java.util.Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                Object keyObject = keys.next();
                String key = String.valueOf(keyObject);
                Object item = jsonObject.get(key);
                String found = findFirstValue(item, targetKey);
                if (found != null) {
                    return found;
                }
            }

            return null;
        }

        if (value instanceof JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                String found = findFirstValue(jsonArray.get(i), targetKey);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    private static boolean hasUsableHours(Object value) {
        Object hoursValue = findValueByKey(value, "hours");
        if (hoursValue == null) {
            return false;
        }

        if (hoursValue instanceof JSONArray jsonArray) {
            if (jsonArray.length() == 0) {
                return false;
            }

            Object firstItem = jsonArray.get(0);
            if (firstItem == null) {
                return false;
            }

            if (firstItem instanceof JSONObject jsonObject && jsonObject.length() == 0) {
                return false;
            }

            if (firstItem instanceof String text && text.trim().isEmpty()) {
                return false;
            }

            return true;
        }

        if (hoursValue instanceof JSONObject jsonObject) {
            return jsonObject.length() != 0;
        }

        if (hoursValue instanceof String text) {
            return !text.trim().isEmpty();
        }

        return false;
    }

    private static Object findValueByKey(Object value, String targetKey) {
        if (value == null) {
            return null;
        }

        if (value instanceof JSONObject jsonObject) {
            if (jsonObject.has(targetKey)) {
                return jsonObject.get(targetKey);
            }

            java.util.Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                Object keyObject = keys.next();
                String key = String.valueOf(keyObject);
                Object found = findValueByKey(jsonObject.get(key), targetKey);
                if (found != null) {
                    return found;
                }
            }
            return null;
        }

        if (value instanceof JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Object found = findValueByKey(jsonArray.get(i), targetKey);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    private static String sanitizeFileName(String value) {
        String sanitized = value.trim();
        sanitized = sanitized.replaceAll("[\\\\/:*?\"<>|]", "_");
        sanitized = sanitized.replaceAll("\\s+", " ");
        sanitized = sanitized.replace(" ", "_");
        if (sanitized.isEmpty()) {
            return "community";
        }
        return sanitized;
    }
}