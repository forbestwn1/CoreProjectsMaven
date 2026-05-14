package com.nosliw.core.application.division.story.design.wizzard;

import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;

public abstract class HAPStoryWizzardParserValueInQuestion  extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPStoryWizzardValueInQuestionair.PARSER_DOMAIN;  }
	
}
