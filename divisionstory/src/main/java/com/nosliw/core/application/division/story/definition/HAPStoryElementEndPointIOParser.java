package com.nosliw.core.application.division.story.definition;

import org.json.JSONObject;

import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

abstract public class HAPStoryElementEndPointIOParser extends HAPStoryElementParser{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementEndPointIO element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		element.setIOType(jsonObj.getString(HAPStoryElementEndPointIO.IOTYPE));
	}

}
