package com.nosliw.datasource.utility.toronto.community;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.nosliw.common.interpolate.HAPStringTemplate;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPUtilityFile;

public class Main3_buildCommunityCenterCodeTable {

    public static void main(String[] args) {
        try {
            JSONObject sourceJson = readSourceJson();
            JSONArray allLocations = sourceJson.optJSONArray("all");
            if (allLocations == null || allLocations.length() == 0) {
                throw new IllegalStateException("No community center entries found in communitylist.json");
            }

            List<String> dataListStr = new ArrayList<String>();
            String dataTemp = HAPUtilityFile.readFile(Main3_buildCommunityCenterCodeTable.class, "community_code_table_data.temp");
            for (int i = 0; i < allLocations.length(); i++) {
                JSONObject ccJsonObj = allLocations.optJSONObject(i);
                dataListStr.add(new HAPStringTemplate(dataTemp)
                     .setParm("district", ccJsonObj.optString("District", ""))
                     .setParm("id", ccJsonObj.optString("ID", ""))
                     .setParm("name", ccJsonObj.optString("Name", ""))
                     .getContent());
            }
            
            String outputContent = new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(Main3_buildCommunityCenterCodeTable.class, "community_code_table.temp"))
					.setParm("dataSet", HAPUtilityJson.buildArrayJson(dataListStr.toArray(new String[0])))
		    		.getContent();

            Files.createDirectories(Utility.COMMUNITY_CENTER_CODETABLE_PATH_ALL.getParent());
            Files.writeString(
            		Utility.COMMUNITY_CENTER_CODETABLE_PATH_ALL,
                    new JSONObject(outputContent).toString(2),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );

            System.out.println("Wrote " + allLocations.length() + " community centers to " + Utility.COMMUNITY_CENTER_CODETABLE_PATH_ALL.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to build community center code table.");
            e.printStackTrace();
        }
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