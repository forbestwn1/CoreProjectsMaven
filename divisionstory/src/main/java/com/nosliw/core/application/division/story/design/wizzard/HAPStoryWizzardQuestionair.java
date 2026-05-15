package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public abstract class HAPStoryWizzardQuestionair extends HAPEntityInfoImp implements HAPEntityParsable{

	public static final String PARSE_DOMAIN = "story.design.wizzard.questionair";
	
	@HAPAttribute
	public static final String TYPE = "type";
	
	@HAPAttribute
	public static final String TAG = "tag";
	
	private String m_type;
	
	private String m_tag;
	
	public HAPStoryWizzardQuestionair(String type, String tag) {
		this(type);
		this.m_tag = tag;
	}

	public HAPStoryWizzardQuestionair(String type) {
		this.m_type = type;
	}

	public String getType() {    return this.m_type;  	}
	
	public boolean isTag(String tag) {    return HAPUtilityBasic.isEquals(m_tag, tag);      }
	public void setTag(String tag) {    this.m_tag = tag;       }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
		jsonMap.put(TAG, this.m_tag);
	}
	
}

@Component
abstract class HAPStoryWizzardQuestionair_Parsable extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPStoryWizzardQuestionair.PARSE_DOMAIN;  }

    protected void parseQuestionair(HAPStoryWizzardQuestionair questionair, JSONObject jsonObj, HAPServiceParseEntity parseService) {
    	questionair.setTag((String)jsonObj.opt(HAPStoryWizzardQuestionair.TAG));
    }
	
}
