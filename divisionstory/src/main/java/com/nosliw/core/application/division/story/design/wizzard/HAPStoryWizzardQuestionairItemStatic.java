package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionairItemStatic extends HAPStoryWizzardQuestionairItem{

	@HAPAttribute
	public static final String VALUE = "value";
	
	private HAPStoryWizzardValueInQuestionair m_value;
    
	public HAPStoryWizzardQuestionairItemStatic() {
    	super(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_STATIC);
	}
	
    public HAPStoryWizzardQuestionairItemStatic(HAPStoryWizzardValueInQuestionair value) {
    	this();
    	this.m_value = value;
	}

    public HAPStoryWizzardValueInQuestionair getValue() {   	return this.m_value;    }
    public void setValue(HAPStoryWizzardValueInQuestionair value) {     this.m_value = value;     }
    
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUE, HAPManagerSerialize.getInstance().toStringValue(this.m_value, HAPSerializationFormat.JSON));
	}
    
}

@Component
class HAPStoryWizzardQuestionairItemStatic_Parsable extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPStoryWizzardQuestionair.PARSE_DOMAIN;  }

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_STATIC;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		
		HAPStoryWizzardQuestionairItemStatic out = new HAPStoryWizzardQuestionairItemStatic();
		
        out.setValue((HAPStoryWizzardValueInQuestionair)jsonObj.optJSONObject(HAPStoryWizzardQuestionairItemStatic.VALUE));
		
		return out;
	}

}

