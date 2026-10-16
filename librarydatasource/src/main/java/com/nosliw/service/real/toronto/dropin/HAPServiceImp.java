package com.nosliw.service.real.toronto.dropin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.common.datasource.HAPExecutableService;
import com.nosliw.core.application.common.datasource.HAPProviderService;
import com.nosliw.core.application.common.datasource.HAPUtilityService;
import com.nosliw.core.application.common.interactive.HAPResultInteractiveTask;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;

public class HAPServiceImp implements HAPExecutableService, HAPProviderService{

	@Override
	public HAPResultInteractiveTask execute(Map<String, HAPData> parms) {
		HAPData locationData = parms.get("location");
		HAPData programData = parms.get("program");
		HAPData fromDateData = parms.get("fromDate");
		HAPData fromTimeData = parms.get("fromTime");
		HAPData toDateData = parms.get("toDate");
		HAPData toTimeData = parms.get("toTime");

		List<String> locationIds = new ArrayList<String>();
		for(Object locationValue : getValues(locationData)) {
			locationIds.add(((JSONObject)locationValue).getString("id"));
		}
		
		List<String> programIds = new ArrayList<String>();
		for(Object programValue : getValues(programData)) {
			programIds.add(((JSONObject)programValue).getString("id"));
		}
		
		LocalDate fromDate = toDate((JSONObject)fromDateData.getValue());
		LocalTime fromTime = toTime((JSONObject)fromTimeData.getValue());
		
		LocalDate toDate = toDate((JSONObject)toDateData.getValue());
		LocalTime toTime = toTime((JSONObject)toTimeData.getValue());

		
		output.put("output", HAPUtilityData.buildDataWrapperFromJson(new JSONObject(HAPUtilityJson.buildMapJson(dataMap))));
		return HAPUtilityService.generateSuccessResult(output);
	}

	private boolean isValid(JSONObject jsonObj, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
		LocalDate date = toDate(jsonObj.getJSONObject("Date"));
		LocalTime time1 = toTime(jsonObj.getJSONObject("fromTime"));
		LocalTime time2 = toTime(jsonObj.getJSONObject("toTime"));
		
		if(date.isAfter(fromDate)&&date.isBefore(toDate)) {
			if(time1.isAfter(fromTime)&&time1.isBefore(toTime)) {
				return true;
			}
		}
		return false;
	}
	
	private String toOutputItem(JSONObject jsonobj) {
		Map<String, String> outMap = new LinkedHashMap<String, String>();
		outMap.put("dataTypeId", "test.map;1.0.0");
		outMap.put("value", jsonobj.toString());
		return HAPUtilityJson.buildMapJson(outMap);
	}
	
	public LocalDate toDate(JSONObject dateDate) {
		JSONObject valueJsonObj = dateDate.getJSONObject("value");
		if(valueJsonObj==null) {
			valueJsonObj = dateDate;
		}
		
		LocalDate date = LocalDate.of(valueJsonObj.getInt("year"), valueJsonObj.getInt("month"), valueJsonObj.getInt("day"));
		return date;
	}
	
	public LocalTime toTime(JSONObject timeObj) {
		JSONObject valueJsonObj = timeObj.getJSONObject("value");
		if(valueJsonObj==null) {
			valueJsonObj = timeObj;
		}
		LocalTime time = LocalTime.of(valueJsonObj.getInt("hour"), valueJsonObj.getInt("minute"), valueJsonObj.getInt("second"));
		return time;
	}
	
	private List<Object> getValues(HAPData data){
		List<Object> out = new ArrayList<Object>();
		if(data.isMultipleValue()) {
			JSONArray valueArray = (JSONArray)data.getValue();
			for(int i=0; i<valueArray.length(); i++) {
				out.add(valueArray.get(i));
			}
		}
		else {
			out.add(data.getValue());
		}
		return out;
	}
}
