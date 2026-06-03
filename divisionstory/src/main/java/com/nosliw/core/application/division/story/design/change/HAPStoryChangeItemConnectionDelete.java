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
public class HAPStoryChangeItemConnectionDelete extends HAPStoryChangeItemConnection{

	private HAPStoryChangeInfoConnection m_connectionInfo;
	
	public HAPStoryChangeItemConnectionDelete() {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_DELETE);
	}
	
	public HAPStoryChangeItemConnectionDelete(HAPStoryIdElement elementIdSource, HAPStoryIdElement elementIdTarget, HAPStoryChangeInfoConnection connectionInfo) {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_DELETE, elementIdSource, elementIdTarget, connectionInfo);
		this.m_connectionInfo = connectionInfo;
	}
	
	@Override
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

@Component
class HAPStoryChangeItemConnectionDelete_HAPEntityParsable extends HAPStoryChangeItemConnection_HAPEntityParsable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_DELETE;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemConnectionDelete changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryChangeItemConnectionDelete out = new HAPStoryChangeItemConnectionDelete();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

