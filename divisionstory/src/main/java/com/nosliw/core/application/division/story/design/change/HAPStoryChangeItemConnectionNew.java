package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;

@HAPEntityWithAttribute
public class HAPStoryChangeItemConnectionNew extends HAPStoryChangeItemConnection{

	private HAPStoryChangeInfoConnection m_connectionInfo;
	
	public HAPStoryChangeItemConnectionNew() {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_NEW);
	}
	
	public HAPStoryChangeItemConnectionNew(HAPStoryIdElement elementIdSource, HAPStoryIdElement elementIdTarget, HAPStoryChangeInfoConnection connectionInfo) {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_NEW, elementIdSource, elementIdTarget);
		this.m_connectionInfo = connectionInfo;
	}
	
	public HAPStoryChangeInfoConnection getConnectionInfo() {    return this.m_connectionInfo;     }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

}
