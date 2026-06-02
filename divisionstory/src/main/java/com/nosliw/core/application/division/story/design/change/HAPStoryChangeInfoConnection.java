package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public abstract class HAPStoryChangeInfoConnection extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.design.change.item.connection";
	
	public final static String CONNECTIONTYPE = "connectionType";
	
	private String m_connectionType;
	
	public HAPStoryChangeInfoConnection(String connectionType) {
		this.m_connectionType = connectionType;
	}
	
	public String getConnectionType() {
		return this.m_connectionType;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CONNECTIONTYPE, this.getConnectionType());
	}
	
}

abstract class HAPStoryChangeInfoConnection_HAPEntityParsable extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeInfoConnection changeItem, HAPServiceParseEntity parseService) {
	}

	@Override
	public String getDomain() {    return HAPStoryChangeInfoConnection.PARSABLEENTITYDOMAIN;  }

}

