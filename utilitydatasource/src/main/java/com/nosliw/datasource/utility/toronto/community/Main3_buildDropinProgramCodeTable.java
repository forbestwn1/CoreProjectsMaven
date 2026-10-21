package com.nosliw.datasource.utility.toronto.community;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.nosliw.common.interpolate.HAPStringTemplate;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPUtilityFile;

public class Main3_buildDropinProgramCodeTable {

    public static void main(String[] args) {
        try {
        	HAPManagerCommunitiyCenterDropin manager = new HAPManagerCommunitiyCenterDropin();
        	
            List<String> dataListStr = new ArrayList<String>();
            String dataTemp = HAPUtilityFile.readFile(Main3_buildDropinProgramCodeTable.class, "dropin_code_table_data.temp");
        	for(String programId : manager.getAllPrograms()) {
                dataListStr.add(new HAPStringTemplate(dataTemp)
                        .setParm("category", manager.getCategoryByProgramId(programId))
                        .setParm("id", programId)
                        .setParm("name", programId)
                        .getContent());
        	}
        	
            String outputContent = new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(Main3_buildDropinProgramCodeTable.class, "dropin_code_table.temp"))
					.setParm("dataSet", HAPUtilityJson.buildArrayJson(dataListStr.toArray(new String[0])))
		    		.getContent();

            Files.createDirectories(Utility.COMMUNITY_CENTER_CODETABLE_PATH.getParent());
            Files.writeString(
            		Utility.COMMUNITY_CENTER_CODETABLE_PATH_ALL,
                    new JSONObject(outputContent).toString(2),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );

            System.out.println("Wrote " + manager.getAllPrograms().size() + " community centers to " + Utility.COMMUNITY_CENTER_CODETABLE_PATH.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to build community center code table.");
            e.printStackTrace();
        }
    }
}
