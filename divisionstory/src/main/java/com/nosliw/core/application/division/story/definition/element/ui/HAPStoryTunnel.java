package com.nosliw.core.application.division.story.definition.element.ui;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializableImp;

//a tunnel is a connection between two end point
public class HAPStoryTunnel extends HAPSerializableImp{

	public final static String END1 = "end1";
	
	public final static String END2 = "end2";
	
	//path to end point
	private HAPPath m_end1;
	
	//path to end point
	private HAPPath m_end2;

	public HAPStoryTunnel() {}
	
	public HAPStoryTunnel(HAPPath end1, HAPPath end2) {
		this.m_end1 = end1;
		this.m_end2 = end2;
	}
	
	public HAPPath getEnd1() {    return this.m_end1;      }
	public HAPPath getEnd2() {    return this.m_end2;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(END1, this.m_end1.toString());
		jsonMap.put(END2, this.m_end2.toString());
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
        this.m_end1 = new HAPPath(jsonObj.getString(END1));	
        this.m_end2 = new HAPPath(jsonObj.getString(END2));	
		return true;
	}	
}
