package com.nosliw.core.application.division.story.design;

import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;

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
