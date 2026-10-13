package com.nosliw.datasource.utility.toronto.community;

import static java.util.Map.entry;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPUtilityJson;

public class Main_loadDropinProgram {

	private static Map<String, Integer> dayOfWeek = Map.ofEntries(
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
			String ccName = ccDropinMan.getCommnityCenterNameById(ccId);
			for(String categaryId : ccDropinMan.getAllProgramCategary()) {
				Map<String, List<String>> programResults = new LinkedHashMap<String, List<String>>(); 
				
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
				        			String programId = ccDropinMan.getProgramIdByName(program);
				        			
				        			if(programId==null){
				        				System.out.println("Program not defined:   " + program);
				        				continue;
				        			}
				        			
				        			List<String> programList = programResults.get(program);
				        			if(programList==null) {
				        				programList = new ArrayList<String>();
				        				programResults.put(program, programList);
				        			}
				        			
				        			JSONArray timesJsonArray = programJsnObj.getJSONArray("times");
				        			for(int m=0; m<timesJsonArray.length(); m++) {
				        				JSONObject timeJonsObj = timesJsonArray.getJSONObject(i);
				        				
				        				Map<String, String> programMap = new LinkedHashMap<String, String>();
				        				
				        				programMap.put("CommunityCenter", Utility.buildData("test.entity;1.0.0", HAPUtilityJson.buildMapJson(Map.ofEntries(entry("name", ccName), entry("id", ccId)))));
				        				programMap.put("Program", Utility.buildData("test.entity;1.0.0", HAPUtilityJson.buildMapJson(Map.ofEntries(entry("name", program), entry("id", programId)))));
				        				
				        				LocalDate date = baseDate.plusDays(dayOfWeek.get(timeJonsObj.getString("day")));
				        				programMap.put("Date", Utility.buildData("test.date;1.0.0", HAPUtilityJson.buildMapJson(
				        						Map.ofEntries(entry("year", date.getYear()+""), entry("month", date.getMonth()+""), entry("date", date.getDayOfMonth()+"")), 
				        						Map.ofEntries(entry("year", Integer.class), entry("month", Integer.class), entry("date", Integer.class)))
				        				));
				        				
				        				String timeStr = timeJonsObj.getString("title");
				        				String[] timeStrs = timeStr.split("-");
				        				
				        		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
				        		        LocalTime fromTime = LocalTime.parse(timeStrs[0].trim(), formatter);
				        		        LocalTime toTime = LocalTime.parse(timeStrs[1].trim(), formatter);

				        				programMap.put("fromTime", Utility.buildData("test.time;1.0.0", HAPUtilityJson.buildMapJson(
				        						Map.ofEntries(entry("hour", fromTime.getHour()+""), entry("minute", fromTime.getMinute()+""), entry("second", fromTime.getSecond()+"")), 
				        						Map.ofEntries(entry("hour", Integer.class), entry("minute", Integer.class), entry("second", Integer.class)))
				        				));
				        				programMap.put("toTime", Utility.buildData("test.time;1.0.0", HAPUtilityJson.buildMapJson(
				        						Map.ofEntries(entry("hour", toTime.getHour()+""), entry("minute", toTime.getMinute()+""), entry("second", toTime.getSecond()+"")), 
				        						Map.ofEntries(entry("hour", Integer.class), entry("minute", Integer.class), entry("second", Integer.class)))
				        				));
				        		        
				        				programList.add(HAPUtilityJson.buildMapJson(programMap));
				        			}
				        			
				        		}
				        		
				        	}
				        }
		        	}
		        }
		        
		        for(String program : programResults.keySet()) {
		        	writeProgram(ccId, categaryId, program, programResults.get(program));
		        }
			}
		}
	}

	private static void writeProgram(String commiunityId, String categary, String program, List<String> data) {
		try {
			Path targetDir = Utility.DROPIN_DATASOURCEDATA_PATH.resolve(commiunityId).resolve(categary);
			Files.createDirectories(targetDir);
			String safeProgramName = program.replaceAll("[\\/:*?\"<>|]", "_");
			Path targetFile = targetDir.resolve(safeProgramName + ".json");
			String json = HAPUtilityJson.buildArrayJson(data.toArray(new String[0]));
			Files.writeString(targetFile, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}