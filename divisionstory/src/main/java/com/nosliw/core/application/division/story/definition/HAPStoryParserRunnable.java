package com.nosliw.core.application.division.story.definition;

import org.json.JSONObject;

import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

abstract public class HAPStoryParserRunnable  extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryRunnable runnable, HAPServiceParseEntity parseService) {
		runnable.buildEntityInfoByJson(jsonObj);
	}
	
	@Override
	public String getDomain() {   return HAPStoryRunnable.PARSABLEENTITYDOMAIN;   }

}
