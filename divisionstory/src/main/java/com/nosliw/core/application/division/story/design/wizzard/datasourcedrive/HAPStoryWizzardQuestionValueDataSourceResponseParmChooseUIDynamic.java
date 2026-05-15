package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic extends HAPStoryWizzardValueInQuestionairImp{

	private HAPStoryWizzardUITagInfo m_uiTagInfo;
	
	public HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic(HAPStoryWizzardUITagInfo uiTagInfo) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMUITAG);
		this.m_uiTagInfo = uiTagInfo;
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

