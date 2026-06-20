package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String ENTITYINFO = "entityInfo";
	
	private HAPEntityInfo m_entityInfo;
	
	public HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_ENTITYINFO);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic(HAPEntityInfo entityInfo) {
		this();
		this.m_entityInfo = entityInfo;
	}
	
	void setEntityInfo(HAPEntityInfo entityInfo) {
		this.m_entityInfo = entityInfo;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_entityInfo!=null) {
			jsonMap.put(ENTITYINFO, this.m_entityInfo.toStringValue(HAPSerializationFormat.JSON));
		}
	}

}

@Component
class HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_ENTITYINFO;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic out = new HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic();
		
		JSONObject jsonObj = (JSONObject)obj;
		JSONObject entityInfoJsonObj = jsonObj.optJSONObject(HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic.ENTITYINFO);
		if(entityInfoJsonObj!=null) {
			HAPEntityInfo entityInfo = new HAPEntityInfoImp();
			entityInfo.buildObject(entityInfoJsonObj, HAPSerializationFormat.JSON);
			out.setEntityInfo(entityInfo);
		}
		
		return out;
	}

}
