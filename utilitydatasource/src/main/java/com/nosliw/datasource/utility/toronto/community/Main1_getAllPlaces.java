package com.nosliw.datasource.utility.toronto.community;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.JSONTokener;

import com.nosliw.common.serialization.HAPUtilityJson;

public class Main1_getAllPlaces {

    private static final String BASE_URL = "https://www.toronto.ca/data/parks/live/locations/%d/info.json";
    private static final Path EXPORT_DIR = Paths.get("C:\\temp\\export\\alllocations");

    public static void main(String[] args) {
        try {
            Files.createDirectories(EXPORT_DIR);
        } catch (IOException e) {
            System.err.println("Unable to create export directory: " + EXPORT_DIR);
            e.printStackTrace();
            return;
        }

        int savedCount = 0;
        for (int id = 0; id <= 5000; id++) {
            if (saveIfValid(id)) {
                savedCount++;
            }
        }

        System.out.println("Completed. Saved " + savedCount + " valid files to " + EXPORT_DIR.toAbsolutePath());
    }

    private static boolean saveIfValid(int id) {
        String urlString = String.format(BASE_URL, id);
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Accept", "application/json");

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return false;
            }

            String responseBody;
            try (InputStream inputStream = connection.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                responseBody = HAPUtilityJson.formatJson(builder.toString());
            }

            if (responseBody == null || responseBody.trim().isEmpty()) {
                return false;
            }

            try {
                new JSONTokener(responseBody).nextValue();
            } catch (Exception e) {
                return false;
            }

            Path targetFile = EXPORT_DIR.resolve(String.format("info_%d.json", id));
            Files.writeString(
                    targetFile,
                    responseBody,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );

            System.out.println("Saved valid response for id " + id + " -> " + targetFile);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}