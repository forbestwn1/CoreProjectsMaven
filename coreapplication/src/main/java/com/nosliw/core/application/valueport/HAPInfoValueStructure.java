package com.nosliw.core.application.valueport;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPInfoValueStructure extends HAPSerializableImp{
	
	@HAPAttribute
	public static String VALUESTRUCTUREID = "valueStructureId";
	@HAPAttribute
	public static String PRIORITY = "priority";

	private String m_valueStructureId;
	
	private double m_priority;

	public HAPInfoValueStructure() {}

	public HAPInfoValueStructure(String valueStructureId, double priority) {
		this.m_valueStructureId = valueStructureId;
		this.m_priority = priority;
	}
	
	public String getValueStructureId() {    return this.m_valueStructureId;     }
	
	public double getPriority() {   return this.m_priority;    }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUESTRUCTUREID, this.getValueStructureId());
		jsonMap.put(PRIORITY, this.getPriority()+"");
		typeJsonMap.put(PRIORITY, Double.class);
	}

	@Override
	protected boolean buildObjectByJson(Object obj){
		JSONObject jsonObj = (JSONObject)obj;
		this.m_valueStructureId = (String)jsonObj.opt(VALUESTRUCTUREID);
		this.m_priority = jsonObj.optDouble(PRIORITY, 0);
		return true;  
	}

}
