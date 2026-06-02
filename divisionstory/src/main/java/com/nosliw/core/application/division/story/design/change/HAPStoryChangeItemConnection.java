package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public abstract class HAPStoryChangeItemConnection extends HAPStoryChangeItem{

	@HAPAttribute
	public static final String ELEMENTIDSOURCE = "elementRefSource";

	@HAPAttribute
	public static final String ELEMENTIDTARGET = "elementRefTarget";

	@HAPAttribute
	public static final String CONNECTIONINFO = "connectionInfo";

	private HAPStoryIdElement m_elementIdSource;
	
	private HAPStoryIdElement m_elementIdTarget;
	
	private HAPStoryChangeInfoConnection m_connectionInfo;
	
	
	public HAPStoryChangeItemConnection(String changeType) {
		super(changeType);
	}
	
	public HAPStoryChangeItemConnection(String changeType, HAPStoryIdElement elementIdSource, HAPStoryIdElement elementIdTarget, HAPStoryChangeInfoConnection connectionInfo) {
		this(changeType);
		this.m_elementIdSource = elementIdSource;
		this.m_elementIdTarget = elementIdTarget;
		this.m_connectionInfo = connectionInfo;
	}
	
	public HAPStoryIdElement getSourceElementId() {  return this.m_elementIdSource; } 
	public void setSourceElementId(HAPStoryIdElement elementId) {     this.m_elementIdSource = elementId;      }
	
	public HAPStoryIdElement getTargetElementId() {  return this.m_elementIdTarget; } 
	public void setTargetElementId(HAPStoryIdElement elementId) {     this.m_elementIdTarget = elementId;      }

	public HAPStoryChangeInfoConnection getConnectionInfo() {    return this.m_connectionInfo;     }
	public void setConnectionInfo(HAPStoryChangeInfoConnection connectionInfo) {     this.m_connectionInfo = connectionInfo;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENTIDSOURCE, HAPUtilityJson.buildJson(this.m_elementIdSource, HAPSerializationFormat.JSON));
		jsonMap.put(ELEMENTIDTARGET, HAPUtilityJson.buildJson(this.m_elementIdTarget, HAPSerializationFormat.JSON));
		jsonMap.put(CONNECTIONINFO, HAPUtilityJson.buildJson(this.m_connectionInfo, HAPSerializationFormat.JSON));
	}
}

abstract class HAPStoryChangeItemConnection_HAPEntityParsable extends HAPStoryChangeItem__HAPEntityParsable{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemConnection changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
		
		HAPStoryIdElement elementIdSource = new HAPStoryIdElement();
		elementIdSource.buildObject(jsonObj.getJSONObject(HAPStoryChangeItemConnection.ELEMENTIDSOURCE), HAPSerializationFormat.JSON);
		changeItem.setSourceElementId(elementIdSource);
		
		HAPStoryIdElement elementIdTarget = new HAPStoryIdElement();
		elementIdTarget.buildObject(jsonObj.getJSONObject(HAPStoryChangeItemConnection.ELEMENTIDTARGET), HAPSerializationFormat.JSON);
		changeItem.setTargetElementId(elementIdTarget);
		
		JSONObject connectionInfoJsonObj = jsonObj.optJSONObject(HAPStoryChangeItemConnection.CONNECTIONINFO);
		if(connectionInfoJsonObj!=null) {
			changeItem.setConnectionInfo((HAPStoryChangeInfoConnection)parseService.parseEntityJSONImplicitAttribute(connectionInfoJsonObj, HAPStoryChangeInfoConnection.CONNECTIONTYPE, HAPStoryChangeInfoConnection.PARSABLEENTITYDOMAIN));
		}
	}
	
}
