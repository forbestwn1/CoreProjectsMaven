package com.nosliw.datasource.utility.toronto.community;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.nosliw.common.utils.HAPUtilityFile;

public class HAPManagerCommunitiyCenterDropin {

	private List<String> ccIds;
	private Map<String, String> nameById; 
	
	public List<String> getAllCommunityCenterIds(){
		if(ccIds==null) {
			ccIds = new ArrayList<String>();
			nameById = new LinkedHashMap<String, String>();
			
			if(!Files.exists(Utility.COMMUNITY_CENTER_CODETABLE_PATH)) {
				throw new IllegalStateException("Community center codetable not found: " + Utility.COMMUNITY_CENTER_CODETABLE_PATH);
			}
			try {
				String content = Files.readString(Utility.COMMUNITY_CENTER_CODETABLE_PATH, StandardCharsets.UTF_8);
				JSONObject root = new JSONObject(new JSONTokener(content));
				JSONArray dataSet = root.optJSONArray("dataSet");
				if(dataSet != null) {
					for(int i=0; i<dataSet.length(); i++) {
						JSONObject ele = dataSet.optJSONObject(i);
						if(ele == null) {
							continue;
						}
						JSONObject value = ele.optJSONObject("value");
						if(value != null && value.has("id")) {
							String id = String.valueOf(value.opt("id"));
							ccIds.add(id);
							nameById.put(id, String.valueOf(value.opt("name")));
						}
					}
				}
			} catch(Exception e) {
				throw new IllegalStateException("Failed to read community center IDs from codetable: " + Utility.COMMUNITY_CENTER_CODETABLE_PATH, e);
			}
		}
		
		return ccIds;
	}
	
	public String getCommnityCenterNameById(String id) {
		return nameById.get(id); 
	}
	
	private List<String> categories;
	public List<String> getAllProgramCategary(){
		if(categories==null) {
			categories = new ArrayList<>();
			if(!Files.exists(Utility.PROGRAM_CODETABLE_PATH)) {
				throw new IllegalStateException("Program codetable not found: " + Utility.PROGRAM_CODETABLE_PATH);
			}
			try {
				String content = Files.readString(Utility.PROGRAM_CODETABLE_PATH, StandardCharsets.UTF_8);
				JSONObject root = new JSONObject(new JSONTokener(content));
				JSONArray dataSet = root.optJSONArray("dataSet");
				if(dataSet != null) {
					for(int i=0; i<dataSet.length(); i++) {
						JSONObject ele = dataSet.optJSONObject(i);
						if(ele == null) {
							continue;
						}
						JSONObject value = ele.optJSONObject("value");
						if(value == null) {
							continue;
						}
						String categary = (String)value.opt("category");
						if(categary!=null) {
							if(!categories.contains(categary)) {
								categories.add(categary);
							}
						}
					}
				}
			} catch(Exception e) {
				throw new IllegalStateException("Failed to read program categories from codetable: " + Utility.PROGRAM_CODETABLE_PATH, e);
			}
		}
		return categories;
	}

	Map<String, String> idByName;
	public String getProgramIdByName(String name) {
		if(name == null) {
			return null;
		}

		if(idByName==null) {
			idByName = new LinkedHashMap<String, String>();
			String content = HAPUtilityFile.readFile(HAPManagerCommunitiyCenterDropin.class, "dropprogrammapping.json");
			JSONObject jsonObj = new JSONObject(content);
			for(Object k : jsonObj.keySet()) {
				String key = (String)k;
				JSONArray jsonArray = jsonObj.getJSONArray(key);
				for(int i=0; i<jsonArray.length(); i++) {
					idByName.put(jsonArray.getString(i), key);
				}
			}
		}
		return idByName.get(name);
	}

}