package com.nosliw.common.variable;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public interface HAPIdVariable extends HAPSerializable, HAPEntityParsable{

	public static final String PARSER_DOMAIN = "variable.id";
	
	@HAPAttribute
	public static String TYPE = "type";
	
	String getType();

	public static HAPIdVariable parseVariableIdJson(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPIdVariable)parseService.parseEntityJSONImplicitAttribute(jsonObj, HAPIdVariable.TYPE, PARSER_DOMAIN);
	}
	
}
