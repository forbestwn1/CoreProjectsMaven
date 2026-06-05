package com.nosliw.core.application.division.story.definition;

import org.json.JSONObject;

import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

abstract public class HAPStoryElementParser  extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElement element, HAPServiceParseEntity parseService) {
		String eleIdKey = (String)jsonObj.opt(HAPStoryElement.ELEMENTID);
		if(eleIdKey!=null) {
			element.setElementId(new HAPStoryIdElement(eleIdKey));
		}
		
		element.setChildren((HAPStoryContainerChildrenElementsAttributes)HAPStoryContainerChildrenElements.parseChildrenContainer(jsonObj.getJSONObject(HAPStoryElement.CHILDREN), parseService));
	}
	
	@Override
	public String getDomain() {   return HAPStoryElement.PARSABLEENTITYDOMAIN;   }

}

