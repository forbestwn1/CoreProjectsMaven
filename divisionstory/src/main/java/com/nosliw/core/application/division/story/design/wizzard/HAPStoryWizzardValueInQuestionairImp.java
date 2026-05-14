package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

public abstract class HAPStoryWizzardValueInQuestionairImp extends HAPSerializableImp implements HAPStoryWizzardValueInQuestionair{

	@HAPAttribute
	public static final String VALUETYPE = "valueType";
	
	private String m_valueType;
	
	public HAPStoryWizzardValueInQuestionairImp(String valueType) {
		this.m_valueType = valueType;
	}
	
	@Override
	public String getValueType() {
		return this.m_valueType;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUETYPE, this.m_valueType);
	}
	
}
