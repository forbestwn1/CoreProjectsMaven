package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;

public abstract class HAPStoryWizzardQuestionairItem extends HAPStoryWizzardQuestionair{

	@HAPAttribute
	public static final String VALUETYPE = "valueType";

	public HAPStoryWizzardQuestionairItem(String type) {
		super(type);
	}

	public HAPStoryWizzardQuestionairItem(String type, String tag) {
		super(type, tag);
	}
	
	abstract public String getValueType();

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUETYPE, this.getValueType());
	}
}
