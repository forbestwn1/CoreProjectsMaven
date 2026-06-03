package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPUtilityJson;

public class HAPStoryWizzardUITagInfo extends HAPSerializableImp{

	public static final String TAGNAME = "tagName";
	
	public static final String ATTRIBUTES = "attribute";
	
	private String m_tagName;
	
	private Map<String, String> m_attributes;
	
	public HAPStoryWizzardUITagInfo() {
		this.m_attributes = new LinkedHashMap<String, String>();
	}
	
	public HAPStoryWizzardUITagInfo(String tagName) {
		this();
		this.m_tagName = tagName;
	}
	
	public String getTagName() {    return this.m_tagName;	}
	
	public Map<String, String> getAttributes(){    return this.m_attributes;  	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(TAGNAME, this.m_tagName);
		jsonMap.put(ATTRIBUTES, HAPUtilityJson.buildMapJson(m_attributes));
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){  
		JSONObject jsonObj = (JSONObject)json;
		this.m_tagName = jsonObj.getString(TAGNAME);
		
		JSONObject attrsJsonObj = jsonObj.getJSONObject(ATTRIBUTES);
		for(Object key : attrsJsonObj.keySet()) {
			String name = (String)key;
			String value = attrsJsonObj.getString(name);
			this.m_attributes.put(name, value);
		}
		
		return true;
	}
}
