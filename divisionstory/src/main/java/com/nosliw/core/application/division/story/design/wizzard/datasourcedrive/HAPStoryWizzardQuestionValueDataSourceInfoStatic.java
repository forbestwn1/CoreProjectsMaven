package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.application.entity.service.HAPServiceProfile;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	private HAPServiceProfile m_dataSrouceProfile;
	
	public HAPStoryWizzardQuestionValueDataSourceInfoStatic(HAPServiceProfile dataSrouceProfile) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEINFO);
		this.m_dataSrouceProfile = dataSrouceProfile;
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

