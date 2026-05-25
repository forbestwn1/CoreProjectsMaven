package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	private HAPEntityInfo m_entityInfo;
	
	public HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic(HAPEntityInfo entityInfo) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_ENTITYINFO);
		this.m_entityInfo = entityInfo;
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

