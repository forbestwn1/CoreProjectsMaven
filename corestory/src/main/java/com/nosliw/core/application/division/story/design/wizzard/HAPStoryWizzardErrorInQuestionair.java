package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPStoryWizzardErrorInQuestionair extends HAPSerializableImp{

	@HAPAttribute
	public static final String MESSAGE = "message";
	
	private String m_message;
	
	public HAPStoryWizzardErrorInQuestionair() {
	}

	public HAPStoryWizzardErrorInQuestionair(String message) {
		this.m_message = message;
	}
	
	public String getMessage() {
		return this.m_message;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(MESSAGE, m_message);
	}	
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_message = (String)jsonObj.opt(MESSAGE);
		return true;  
	}
	
}
