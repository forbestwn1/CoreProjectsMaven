package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmRequest;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String REQUESTPARMDEF = "requestParmDef";
	
	private HAPDefinitionParmRequest m_requestParmDef;

	public HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMINFO);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic(HAPDefinitionParmRequest parmDef) {
		this();
		this.m_requestParmDef = parmDef;
	}
	
	public HAPDefinitionParmRequest getParmDefinition() {     return this.m_requestParmDef;      }
	public void setParmDefinition(HAPDefinitionParmRequest parmDef) {     this.m_requestParmDef = parmDef;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_requestParmDef!=null) {
			jsonMap.put(REQUESTPARMDEF, this.m_requestParmDef.toStringValue(HAPSerializationFormat.JSON));
		}
	}

}

@Component
class HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMINFO;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic out = new HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject parmDefJsonObj = jsonObj.optJSONObject(HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic.REQUESTPARMDEF);
		if(parmDefJsonObj!=null) {
			out.setParmDefinition(HAPDefinitionParmRequest.buildParmFromObject(parmDefJsonObj, parseService));
		}
		
		return out;
	}
}
