package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryChangeItemConnectionNew extends HAPStoryChangeItemConnection{

	public HAPStoryChangeItemConnectionNew() {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_NEW);
	}
	
	public HAPStoryChangeItemConnectionNew(HAPStoryIdElement elementIdSource, HAPStoryIdElement elementIdTarget, HAPStoryChangeInfoConnection connectionInfo) {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_NEW, elementIdSource, elementIdTarget, connectionInfo);
	}
	
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

@Component
class HAPStoryChangeItemConnectionNew_HAPEntityParsable extends HAPStoryChangeItemConnection_HAPEntityParsable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_NEW;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemConnectionNew changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryChangeItemConnectionNew out = new HAPStoryChangeItemConnectionNew();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
