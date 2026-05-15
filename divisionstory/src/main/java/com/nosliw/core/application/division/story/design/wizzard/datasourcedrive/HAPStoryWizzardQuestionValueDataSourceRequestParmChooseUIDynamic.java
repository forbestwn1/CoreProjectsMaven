package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseUIDynamic extends HAPStoryWizzardValueInQuestionairImp{

	private HAPStoryWizzardUITagInfo m_uiTagInfo;
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseUIDynamic(HAPStoryWizzardUITagInfo uiTagInfo) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMUITAG);
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

