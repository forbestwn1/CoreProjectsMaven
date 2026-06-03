package com.nosliw.core.application.division.story.design.wizzard;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public interface HAPStoryWizzardValueInQuestionair extends HAPEntityParsable{

	public static final String PARSER_DOMAIN = "design.wizzard.question.value";

	@HAPAttribute
	public static final String VALUETYPE = "valueType";
	
	String getValueType();

	public static HAPStoryWizzardValueInQuestionair parseEntity(JSONObject objJson, HAPServiceParseEntity parseService) {
		return (HAPStoryWizzardValueInQuestionair)parseService.parseEntityJSONImplicitAttribute(objJson, VALUETYPE, PARSER_DOMAIN);
	}
	
}
