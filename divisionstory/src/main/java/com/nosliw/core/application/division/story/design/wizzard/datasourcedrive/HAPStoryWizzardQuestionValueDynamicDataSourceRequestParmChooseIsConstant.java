package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionair;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDynamicDataSourceRequestParmChooseIsConstant extends HAPSerializableImp implements HAPStoryWizzardValueInQuestionair{

	public HAPStoryWizzardQuestionValueDynamicDataSourceRequestParmChooseIsConstant() {
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

}

