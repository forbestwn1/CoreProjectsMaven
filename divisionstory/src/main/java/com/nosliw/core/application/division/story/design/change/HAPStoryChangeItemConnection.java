package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.HAPStoryIdElement;
import com.nosliw.core.application.division.story.HAPStoryReferenceElementWrapper;
import com.nosliw.core.application.division.story.brick.HAPStoryReferenceElement;

@HAPEntityWithAttribute
public abstract class HAPStoryChangeItemConnection extends HAPStoryChangeItem{

	@HAPAttribute
	public static final String ELEMENTREFSOURCE = "elementRefSource";

	@HAPAttribute
	public static final String ELEMENTREFTARGET = "elementRefTarget";

	@HAPAttribute
	public static final String CONNECTIONTYPE = "connectionType";

	private HAPStoryReferenceElementWrapper m_elementRefSource;
	
	private HAPStoryReferenceElementWrapper m_elementRefTarget;
	
	public HAPStoryChangeItemConnection(String changeType) {
		super(changeType);
	}
	
	public HAPStoryChangeItemConnection(String changeType, HAPStoryReferenceElement elementRefSource, HAPStoryReferenceElement elementRefTarget) {
		this(changeType);
		this.m_elementRefSource = new HAPStoryReferenceElementWrapper(elementRefSource);
		this.m_elementRefTarget = new HAPStoryReferenceElementWrapper(elementRefTarget);
	}
	
	public HAPStoryIdElement getSourceElementId() {  return this.m_elementRefSource.getElementId(); } 
	public HAPStoryIdElement getTargetElementId() {  return this.m_elementRefTarget.getElementId(); } 
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		
		this.m_elementRefSource = new HAPStoryReferenceElementWrapper();
		this.m_elementRefSource.buildObject(jsonObj.getJSONObject(ELEMENTREFSOURCE), HAPSerializationFormat.JSON);
		
		this.m_elementRefTarget = new HAPStoryReferenceElementWrapper();
		this.m_elementRefTarget.buildObject(jsonObj.getJSONObject(ELEMENTREFTARGET), HAPSerializationFormat.JSON);
		
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENTREFSOURCE, HAPUtilityJson.buildJson(this.m_elementRefSource, HAPSerializationFormat.JSON));
		jsonMap.put(ELEMENTREFTARGET, HAPUtilityJson.buildJson(this.m_elementRefTarget, HAPSerializationFormat.JSON));
	}
}
