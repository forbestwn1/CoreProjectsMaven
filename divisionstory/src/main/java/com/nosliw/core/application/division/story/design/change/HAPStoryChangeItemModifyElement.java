package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

abstract public class HAPStoryChangeItemModifyElement extends HAPStoryChangeItem{

	@HAPAttribute
	public static final String TARGETELEMENTID = "targetElementId";

	private HAPStoryIdElement m_targetElementId;
	
	public HAPStoryChangeItemModifyElement(String type) {
		super(type);
	}
	
	public HAPStoryChangeItemModifyElement(String type, HAPStoryIdElement targetElementId) {
		this(type);
		this.m_targetElementId = targetElementId;
	}
	
	public HAPStoryIdElement getTargetElementId() {  return this.m_targetElementId; } 
	public void setTargetElementId(HAPStoryIdElement elementId) {    this.m_targetElementId = elementId;    }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TARGETELEMENTID, HAPUtilityJson.buildJson(this.m_targetElementId, HAPSerializationFormat.JSON));
	}
}

abstract class HAPStoryChangeItemModifyElement_HAPEntityParsable extends HAPStoryChangeItem__HAPEntityParsable{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemModifyElement changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
		
		HAPStoryIdElement targetElementId = new HAPStoryIdElement();
		targetElementId.buildObject(jsonObj.getJSONObject(HAPStoryChangeItemModifyElement.TARGETELEMENTID), HAPSerializationFormat.JSON);
		changeItem.setTargetElementId(targetElementId);
	}
	
}
