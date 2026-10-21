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
	private Map<String, String> ccNameById; 
	
	private List<String> categories;
	private Map<String, String> programIdByName;
	private List<String> programIds;
	private Map<String, String> categoryIdByProgramId;
	
	
	public List<String> getAllCommunityCenterIds(){
		if(ccIds==null) {
			ccIds = new ArrayList<String>();
			ccNameById = new LinkedHashMap<String, String>();
			
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
							ccNameById.put(id, String.valueOf(value.opt("name")));
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
		return ccNameById.get(id); 
	}
	
	public List<String> getAllProgramCategary(){
		if(this.categories==null) {
			this.buildProgramData();
		}
		return this.categories;
	}

	public List<String> getAllPrograms(){
		if(this.programIds==null) {
			this.buildProgramData();
		}
		return this.programIds;
	}

	public String getCategoryByProgramId(String programId) {
		if(this.categoryIdByProgramId==null) {
			this.buildProgramData();
		}
		return this.categoryIdByProgramId.get(programId);
	}
	
	public String getProgramIdByName(String name) {
		if(name == null) {
			return null;
		}

		if(this.programIdByName==null) {
			this.buildProgramData();
		}
		return this.programIdByName.get(name);
	}

	private void buildProgramData() {
		this.categories = new ArrayList<String>();
		this.programIdByName = new LinkedHashMap<String, String>();
		this.programIds = new ArrayList<String>();
		this.categoryIdByProgramId = new LinkedHashMap<String, String>();
		
		JSONArray categorysJsonArray = new JSONArray(HAPUtilityFile.readFile(HAPManagerCommunitiyCenterDropin.class, "dropprogrammapping.json"));
		for(int i=0; i<categorysJsonArray.length(); i++) {
			JSONObject categoryJsonObj = categorysJsonArray.getJSONObject(i);
			if(categoryJsonObj.optBoolean("enable", true)) {
				String categoryName = categoryJsonObj.getString("category");
				this.categories.add(categoryName);
				
				JSONObject programsJsonObj = categoryJsonObj.getJSONObject("program");
				for(Object key : programsJsonObj.keySet()) {
					String programId = (String)key;
					this.programIds.add(programId);
					this.categoryIdByProgramId.put(programId, categoryName);
					
					JSONArray programNamesArray = programsJsonObj.getJSONArray(programId);
					for(int j=0; j<programNamesArray.length(); j++) {
						this.programIdByName.put(programNamesArray.getString(j), programId);
					}
				}
			}
		}
	}
	
}