package com.nosliw.core.application.division.story.definition;

import org.json.JSONObject;

import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

abstract public class HAPStoryMetaDataChildElementParser extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryMetaDataChildElement metadata, HAPServiceParseEntity parseService) {
	}

	@Override
	public String getDomain() {    return HAPStoryMetaDataChildElement.PARSABLEENTITYDOMAIN;  }
	
}
