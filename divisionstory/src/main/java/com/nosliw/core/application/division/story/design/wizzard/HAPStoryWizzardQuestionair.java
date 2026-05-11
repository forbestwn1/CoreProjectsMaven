package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;

public abstract class HAPStoryWizzardQuestionair extends HAPEntityInfoImp implements HAPEntityParsable{

	public static final String PARSE_DOMAIN = "story.design.wizzard.questionair";
	
	@HAPAttribute
	public static final String TYPE = "type";
	
	private String m_type;
	
	public HAPStoryWizzardQuestionair(String type) {
		this.m_type = type;
	}
	
	public String getType() {    return this.m_type;  	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
	}
	
}
