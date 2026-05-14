package com.nosliw.core.application.division.story.design.wizzard;

import com.nosliw.core.service.entityparse.HAPEntityParsable;

public interface HAPStoryWizzardValueInQuestionair extends HAPEntityParsable{

	public static final String PARSER_DOMAIN = "design.wizzard.question.value";
	
	String getValueType();
	
}
