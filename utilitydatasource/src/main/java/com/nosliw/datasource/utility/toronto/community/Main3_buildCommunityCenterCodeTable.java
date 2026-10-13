package com.nosliw.datasource.utility.toronto.community;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Main3_buildCommunityCenterCodeTable {

    public static void main(String[] args) {
        try {
            JSONObject sourceJson = readSourceJson();
            JSONArray allLocations = sourceJson.optJSONArray("all");
            if (allLocations == null || allLocations.length() == 0) {
                throw new IllegalStateException("No community center entries found in communitylist.json");
            }

            JSONObject outputJson = loadOrCreateTemplate();
            JSONArray sampleDataSet = outputJson.optJSONArray("dataSet");
            JSONObject sampleItem = (sampleDataSet != null && sampleDataSet.length() > 0) ? sampleDataSet.optJSONObject(0) : null;
            JSONObject sampleValue = sampleItem != null ? sampleItem.optJSONObject("value") : null;

            JSONArray newDataSet = new JSONArray();
            for (int i = 0; i < allLocations.length(); i++) {
                JSONObject communityCenter = allLocations.optJSONObject(i);
                if (communityCenter == null) {
                    continue;
                }

                JSONObject item = new JSONObject();
                item.put("dataTypeId", sampleItem != null ? sampleItem.optString("dataTypeId", "test.entity;1.0.0") : "test.entity;1.0.0");

                JSONObject value = new JSONObject();
                if (sampleValue != null && sampleValue.has("id")) {
                    value.put("id", String.valueOf(communityCenter.optInt("ID", 0)));
                }
                if (sampleValue != null && sampleValue.has("name")) {
                    value.put("name", communityCenter.optString("Name", ""));
                }
                if (sampleValue != null && sampleValue.has("district")) {
                    value.put("district", communityCenter.optString("District", ""));
                }

                item.put("value", value);
                newDataSet.put(item);
            }

            outputJson.put("dataSet", newDataSet);

            Files.createDirectories(Utility.COMMUNITY_CENTER_CODETABLE_PATH.getParent());
            Files.writeString(
            		Utility.COMMUNITY_CENTER_CODETABLE_PATH,
                    outputJson.toString(2),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );

            System.out.println("Wrote " + newDataSet.length() + " community centers to " + Utility.COMMUNITY_CENTER_CODETABLE_PATH.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to build community center code table.");
            e.printStackTrace();
        }
    }

    private static JSONObject loadOrCreateTemplate() throws Exception {
        if (Files.exists(Utility.COMMUNITY_CENTER_CODETABLE_PATH)) {
            String content = Files.readString(Utility.COMMUNITY_CENTER_CODETABLE_PATH, StandardCharsets.UTF_8);
            if (content != null && !content.trim().isEmpty()) {
                return new JSONObject(new JSONTokener(content));
            }
        }

        JSONObject template = new JSONObject();
        template.put("name", "toronto_community");
        template.put("id", "toronto_community");
        template.put("dataType", "test.entity;1.0.0");
        JSONObject info = new JSONObject();
        info.put("category", new JSONArray().put("district"));
        template.put("info", info);

        JSONArray sampleDataSet = new JSONArray();
        JSONObject sampleItem = new JSONObject();
        sampleItem.put("dataTypeId", "test.entity;1.0.0");
        JSONObject sampleValue = new JSONObject();
        sampleValue.put("id", "001");
        sampleValue.put("name", "Cummer Centr");
        sampleValue.put("district", "Scarborough");
        sampleItem.put("value", sampleValue);
        sampleDataSet.put(sampleItem);
        template.put("dataSet", sampleDataSet);
        return template;
    }

    private static JSONObject readSourceJson() throws Exception {
        InputStream inputStream = Main3_buildCommunityCenterCodeTable.class.getResourceAsStream(Utility.COMMUNITY_CENTER_LIST_RESOURCE_PATH);

        if (inputStream == null) {
            throw new FileNotFoundException("Unable to find communitylist.json on the classpath or in the source tree: " + Utility.COMMUNITY_CENTER_LIST_RESOURCE_PATH);
        }

        try (InputStream stream = inputStream) {
            String content = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            return new JSONObject(new JSONTokener(content));
        }
    }
}