package com.nosliw.core.application.division.story.definition;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPServiceParseEntity;

abstract public class HAPStoryParserRunnable  extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryRunnable runnable, HAPServiceParseEntity parseService) {
		runnable.buildEntityInfoByJson(jsonObj);
	}
	
	@Override
	public String getDomain() {   return HAPStoryRunnable.PARSABLEENTITYDOMAIN;   }

}
