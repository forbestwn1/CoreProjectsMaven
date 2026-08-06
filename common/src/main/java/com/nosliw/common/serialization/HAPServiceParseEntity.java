package com.nosliw.common.serialization;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public interface HAPServiceParseEntity {

	//entity type through attribute
	public HAPEntityParsable parseEntityJSONImplicitAttribute(JSONObject jsonObj, String attributNameForEntityType, String domain);
	
	//explicit entity type
	public HAPEntityParsable parseEntityJSONExplicit(JSONObject jsonObj, String entityType);

	public HAPEntityParsable parseEntityJSONExplicit(JSONObject jsonObj, String subType, String domain);

	public HAPEntityParsable parseEntityJSONImplicitAttribute(JSONObject jsonObj);
	
}
