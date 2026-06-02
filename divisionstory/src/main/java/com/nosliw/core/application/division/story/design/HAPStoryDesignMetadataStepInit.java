package com.nosliw.core.application.division.story.design;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryDesignMetadataStepInit extends HAPStoryDesignMetadataStep{

	public HAPStoryDesignMetadataStepInit() {
		super(HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_INIT);
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
class HAPStoryDesignMetadataStepInit_HAPEntityParsable extends HAPStoryParserEntityMetadataStep{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_INIT;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPStoryDesignMetadataStepInit out = new HAPStoryDesignMetadataStepInit();
		return out;
	}

}
