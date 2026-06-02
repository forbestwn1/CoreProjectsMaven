package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeInfoConnection;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryMetaDataChildElement extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.design.change.item.connection.metadata";
	
	public final static String TYPE = "type";

	
	private String m_type;
	
	public HAPStoryMetaDataChildElement(String type) {
		this.m_type = type;
	}
	
	public String getType() {     return this.m_type;      }

	public static HAPStoryMetaDataChildElement parseMetaData(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPStoryMetaDataChildElement)parseService.parseEntityJSONImplicitAttribute(jsonObj, TYPE, PARSABLEENTITYDOMAIN);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
	}
}

abstract class HAPStoryMetaDataChildElement_HAPEntityParsable extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeInfoConnection changeItem, HAPServiceParseEntity parseService) {
	}

	@Override
	public String getDomain() {    return HAPStoryMetaDataChildElement.PARSABLEENTITYDOMAIN;  }

}
