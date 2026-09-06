package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceString extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String STRINGVALUE = "stringValue";

	private String m_stringValue;
	
	public HAPStoryWizzardQuestionValueDataSourceString() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_STRING);
	}
	
	public String getStringValue() {     return this.m_stringValue;      }
	public void setStringValue(String value) {       this.m_stringValue = value;         }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(STRINGVALUE, m_stringValue);
	}
}

@Component
class HAPStoryWizzardQuestionValueDataSourceString_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_STRING;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceString out = new HAPStoryWizzardQuestionValueDataSourceString();
		
		JSONObject jsonObj = (JSONObject)obj;
		out.setStringValue((String)jsonObj.opt(HAPStoryWizzardQuestionValueDataSourceString.STRINGVALUE));
		return out;
	}

}
