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
		if(jsonObj==null) {
			return null;
		}
		String subType = getSubTypeFromAttribute(jsonObj, attributNameForEntityType);
		return this.parseEntityJSONExplicit(jsonObj, subType, domain);
	}
	
	private String getSubTypeFromAttribute(JSONObject jsonObj, String attributNameForEntityType) {
		String out = null;
		String attributeName = attributNameForEntityType!=null?attributNameForEntityType:HAPEntityParsable.ENTITYTYPE;
		String[] segs = attributeName.split("\\.");
		for(int i=0; i<segs.length; i++) {
			if(i==segs.length-1) {
				out = jsonObj.getString(segs[i]);
			}
			else {
				jsonObj = jsonObj.getJSONObject(segs[i]);
			}
		}
		return out;
	}

	//explicit entity type
	public HAPEntityParsable parseEntityJSONExplicit(JSONObject jsonObj, String entityType) {
		if(jsonObj==null || HAPUtilityBasic.isStringEmpty(entityType)) {
			return null;
		}
		return this.m_parsers.get(entityType).parseEntityJson(jsonObj, this);
	}

	public HAPEntityParsable parseEntityJSONExplicit(JSONObject jsonObj, String subType, String domain) {
		if(jsonObj==null) {
			return null;
		}
		String entityType = HAPUtilityNamingConversion.cascadePath(domain, subType);
		return this.m_parsers.get(entityType).parseEntityJson(jsonObj, this);
	}

	public HAPEntityParsable parseEntityJSONImplicitAttribute(JSONObject jsonObj) {
		if(jsonObj==null) {
			return null;
		}
		return this.parseEntityJSONImplicitAttribute(jsonObj, null, null);
	}
	
}
