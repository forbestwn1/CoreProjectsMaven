package com.nosliw.core.application.division.story.design;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryDesignMetadataStepEnd extends HAPStoryDesignMetadataStep{

	public HAPStoryDesignMetadataStepEnd() {
		super(HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_END);
	}

	@Override
	public void clear() {
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

}

@Component
class HAPStoryDesignMetadataStepEnd_HAPEntityParsable extends HAPStoryParserEntityMetadataStep{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_END;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPStoryDesignMetadataStepEnd out = new HAPStoryDesignMetadataStepEnd();
		return out;
	}

}
