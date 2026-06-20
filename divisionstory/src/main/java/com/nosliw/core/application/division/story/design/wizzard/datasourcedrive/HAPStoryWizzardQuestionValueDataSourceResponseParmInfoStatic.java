package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmResponse;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String RESPONSEPARMDEFINITION = "responseParmDefinition";
	
	private HAPDefinitionParmResponse m_responseParmDef;
	
	public HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMINFO);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic(HAPDefinitionParmResponse responseParm) {
		this();
		this.m_responseParmDef = responseParm;
	}

	void setRespsoneParmDefinition(HAPDefinitionParmResponse responseParmDef) {     this.m_responseParmDef = responseParmDef;  	}
	public HAPDefinitionParmResponse getResponseParmDef() {     return this.m_responseParmDef;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_responseParmDef!=null) {
			jsonMap.put(RESPONSEPARMDEFINITION, this.m_responseParmDef.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMINFO;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic out = new HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject responseParmDefJsonObj = jsonObj.optJSONObject(HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic.RESPONSEPARMDEFINITION);
		if(responseParmDefJsonObj!=null) {
			out.setRespsoneParmDefinition(HAPDefinitionParmResponse.parse(responseParmDefJsonObj, parseService));
		}
		
		return out;
	}

}

