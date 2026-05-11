package com.nosliw.core.service.entityparse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

@Component
public class HAPServiceParseEntity {

	private Map<String, HAPParserEntity> m_parsers;
	
	public HAPServiceParseEntity() {
		this.m_parsers = new LinkedHashMap<String, HAPParserEntity>();
	}
	
	@Autowired(required=false)
	private void setParsers(List<HAPParserEntity> parsers) {
		for(HAPParserEntity parser : parsers) {
			this.m_parsers.put(parser.getEntityType(), parser);
		}
	}

	//entity type through attribute
	public HAPEntityParsable parseEntityJSONImplicitAttribute(JSONObject jsonObj, String attributNameForEntityType, String domain) {
		String attributeName = attributNameForEntityType!=null?attributNameForEntityType:HAPEntityParsable.ENTITYTYPE;
		String entityType = jsonObj.getString(attributeName);
		entityType = HAPUtilityNamingConversion.cascadePath(domain, entityType);
		return this.m_parsers.get(entityType).parseEntityJson(jsonObj, this);
	}

	//explicit entity type
	public HAPEntityParsable parseEntityJSONExplicit(JSONObject jsonObj, String entityType) {
		if(jsonObj==null || HAPUtilityBasic.isStringEmpty(entityType)) {
			return null;
		}
		return this.m_parsers.get(entityType).parseEntityJson(jsonObj, this);
		
	}

	public HAPEntityParsable parseEntityJSONImplicitAttribute(JSONObject jsonObj) {
		return this.parseEntityJSONImplicitAttribute(jsonObj, null, null);
	}
	
}
