package com.nosliw.core.application.division.story.design;

import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;

public abstract class HAPStoryParserEntityMetadataStep extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {    return HAPStoryDesignMetadataStep.PARSABLEENTITYDOMAIN;   }

}
