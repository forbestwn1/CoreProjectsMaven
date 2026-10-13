package com.nosliw.datasource.utility.toronto.community;

import static java.util.Map.entry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main_loadDropinProgram {

	private static Map<String, Integer> byDay = Map.ofEntries(
		    entry("monday", 0),
		    entry("tuesday", 1),
		    entry("wednesday", 2),
		    entry("thursday", 3),
		    entry("friday", 4),
		    entry("saturday", 5),
		    entry("sunday", 6)
		);
	
	public static void main(String[] args) {
		
		HAPManagerCommunitiyCenterDropin ccDropinMan = new HAPManagerCommunitiyCenterDropin();		
		
		List<String> ccs = ccDropinMan.getAllCommunityCenterIds();
		System.out.println(ccs);
		List<String> programCategorys = ccDropinMan.getAllProgramCategary();
		System.out.println(programCategorys);
		
		//read
		for(String ccId : ccDropinMan.getAllCommunityCenterIds()) {
			for(String categaryId : ccDropinMan.getAllProgramCategary()) {
				Map<String, List<JSONObject>> programResults = new LinkedHashMap<String, List<JSONObject>>(); 
				
		        String programString = Utility.httpRead("https://www.toronto.ca/data/parks/live/locations/"+ccId+"/"+categaryId+"/info.json");
		        if(programString!=null) {
		        	JSONObject programsJsonObj = new JSONObject(programString);
		        	JSONArray byWeekJsonArray = programsJsonObj.getJSONArray("weeks");
		        	for(int i=0; i<byWeekJsonArray.length(); i++) {
		        		JSONObject weekInfoJsonObj = byWeekJsonArray.getJSONObject(i);
		        		
		        		LocalDate baseDate = LocalDate.parse(weekInfoJsonObj.getString("title"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		        		
				        String urlString = "https://www.toronto.ca/data/parks/live/locations/"+ccId+"/"+categaryId+"/week"+weekInfoJsonObj.getString("json");
				        String content = Utility.httpRead(urlString);
				        if(content!=null) {
				        	JSONObject contentJsonObj = new JSONObject(content);
				        	JSONArray programJsonArray = contentJsonObj.getJSONArray("programs");
				        	for(int j=0; i<programJsonArray.length(); j++) {
				        		JSONArray programsJsonArray = programJsonArray.getJSONObject(i).getJSONArray("days");
				        		for(int k=0; k<programsJsonArray.length(); k++) {
				        			JSONObject programJsnObj = programsJsonArray.getJSONObject(k);
				        			String program = programJsnObj.getString("title");
				        			
				        			List<JSONObject> programList = programResults.get(program);
				        			if(programList==null) {
				        				programList = new ArrayList<JSONObject>();
				        				programResults.put(program, programList);
				        			}
				        			
				        			JSONArray timesJsonArray = programJsnObj.getJSONArray("times");
				        			for(int m=0; m<timesJsonArray.length(); m++) {
				        				Map<String, String> programMap = new LinkedHashMap<String, String>();
				        				programMap.put("", ccId)
				        				
				        				
				        			}
				        			
				        		}
				        		
				        	}
				        }
		        	}
		        }
			}
		}

		
	}

	private static void buildProgramqResult() {
		
	}
	
	
	private static void writeProgram(String commiunityId, String program, Object data) {
		
	}
	
	
	
}
