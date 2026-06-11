package com.nosliw.core.application.division.story.definition.element.ui;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;

//a tunnel is a connection between two end point
public class HAPStoryTunnel extends HAPSerializableImp{

	public final static String SOURCE = "source";
	
	public final static String TARGET = "target";
	
	//path to end point
	private String m_source;
	
	//path to end point
	private String m_target;

	public HAPStoryTunnel() {}
	
	public HAPStoryTunnel(String source, String target) {
		this.m_source = source;
		this.m_target = target;
	}
	
	public String getSource() {    return this.m_source;      }
	public String getTarget() {    return this.m_target;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(SOURCE, this.m_source);
		jsonMap.put(TARGET, this.m_target);
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
        this.m_source = jsonObj.getString(SOURCE);	
        this.m_target = jsonObj.getString(TARGET);	
		return true;
	}	
}
