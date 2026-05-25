package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	private HAPDataTypeCriteria m_dataTypeCriteria;
	
	public HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic(HAPDataTypeCriteria dataTypeCriteria) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEDATACRITERIAINFO);
		this.m_dataTypeCriteria = dataTypeCriteria;
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

