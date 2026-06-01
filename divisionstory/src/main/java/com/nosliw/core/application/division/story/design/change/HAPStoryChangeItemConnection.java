package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;

@HAPEntityWithAttribute
public abstract class HAPStoryChangeItemConnection extends HAPStoryChangeItem{

	@HAPAttribute
	public static final String ELEMENTIDSOURCE = "elementRefSource";

	@HAPAttribute
	public static final String ELEMENTIDTARGET = "elementRefTarget";

	@HAPAttribute
	public static final String CONNECTIONTYPE = "connectionType";

	private HAPStoryIdElement m_elementIdSource;
	
	private HAPStoryIdElement m_elementIdTarget;
	
	public HAPStoryChangeItemConnection(String changeType) {
		super(changeType);
	}
	
	public HAPStoryChangeItemConnection(String changeType, HAPStoryIdElement elementIdSource, HAPStoryIdElement elementIdTarget) {
		this(changeType);
		this.m_elementIdSource = elementIdSource;
		this.m_elementIdTarget = elementIdTarget;
	}
	
	public HAPStoryIdElement getSourceElementId() {  return this.m_elementIdSource; } 
	public HAPStoryIdElement getTargetElementId() {  return this.m_elementIdTarget; } 
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		
		this.m_elementIdSource = new HAPStoryIdElement();
		this.m_elementIdSource.buildObject(jsonObj.getJSONObject(ELEMENTIDSOURCE), HAPSerializationFormat.JSON);
		
		this.m_elementIdTarget = new HAPStoryIdElement();
		this.m_elementIdTarget.buildObject(jsonObj.getJSONObject(ELEMENTIDTARGET), HAPSerializationFormat.JSON);
		
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENTIDSOURCE, HAPUtilityJson.buildJson(this.m_elementIdSource, HAPSerializationFormat.JSON));
		jsonMap.put(ELEMENTIDTARGET, HAPUtilityJson.buildJson(this.m_elementIdTarget, HAPSerializationFormat.JSON));
	}
}
