package com.nosliw.datasource.utility.toronto.community;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HAPManagerCommunitiyCenterDropin {

	public List<String> getAllCommunityCenterIds(){
		List<String> ids = new ArrayList<>();
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
						ids.add(String.valueOf(value.opt("id")));
					}
				}
			}
		} catch(Exception e) {
			throw new IllegalStateException("Failed to read community center IDs from codetable: " + Utility.COMMUNITY_CENTER_CODETABLE_PATH, e);
		}
		return ids;
	}
	
	public List<String> getAllProgramCategary(){
		List<String> categories = new ArrayList<>();
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
		return categories;
	}

	public String getProgramIdByName(String name) {
		if(name == null) {
			return null;
		}
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
					String programName = value.optString("name", "");
					if(programName.equalsIgnoreCase(name.trim())) {
						return value.optString("id", null);
					}
				}
			}
		} catch(Exception e) {
			throw new IllegalStateException("Failed to find program id by name: " + name, e);
		}
		return null;
	}

}