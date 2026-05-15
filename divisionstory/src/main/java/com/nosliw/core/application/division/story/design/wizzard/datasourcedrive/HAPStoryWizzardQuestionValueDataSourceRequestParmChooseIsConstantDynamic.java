package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic extends HAPStoryWizzardValueInQuestionairImp{

	private boolean m_isConstant;
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic(boolean isConstant) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMISCONSTANT);
		this.m_isConstant = isConstant;
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

