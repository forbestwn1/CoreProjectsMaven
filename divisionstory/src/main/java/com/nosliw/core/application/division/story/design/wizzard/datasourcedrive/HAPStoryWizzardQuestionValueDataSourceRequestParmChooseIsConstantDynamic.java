package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String ISCONSTANT = "isConstant";

	private boolean m_isConstant = false;
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMISCONSTANT);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic(boolean isConstant) {
		this();
		this.m_isConstant = isConstant;
	}
	
	public boolean getIsConstant() {     return this.m_isConstant;       }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		Boolean isConstant = (Boolean)jsonObj.get(ISCONSTANT);
		if(isConstant!=null) {
			this.m_isConstant = isConstant;
		}
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ISCONSTANT, this.m_isConstant+"");
		typeJsonMap.put(ISCONSTANT, Boolean.class);
	}

}

@Component
class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMISCONSTANT;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic out = new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic();
		out.buildObject(obj, HAPSerializationFormat.JSON);
		return out;
	}

}

