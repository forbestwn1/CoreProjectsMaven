package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.data.HAPData;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic extends HAPStoryWizzardValueInQuestionairImp{

	private HAPData m_constantData;
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic(HAPData constantData) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE);
		this.m_constantData = constantData;
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

