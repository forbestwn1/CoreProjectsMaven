package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.application.entity.app.databuild.HAPDataBuild;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String DATABUILD = "dataBuild";
	
	private HAPDataBuild m_dataBuild;
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic(HAPDataBuild dataBuild) {
		this();
		this.m_dataBuild = dataBuild;
	}
		
	public HAPDataBuild getDataBuild() {    return this.m_dataBuild;    }
	public void setDataBuild(HAPDataBuild dataBuild) {      this.m_dataBuild = dataBuild;         }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		if(this.m_dataBuild!=null) {
			jsonMap.put(DATABUILD, this.m_dataBuild.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic out = new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic();
		
		JSONObject jsonObj = (JSONObject)obj;

		out.setDataBuild(HAPDataBuild.buildDataBuild(jsonObj.optJSONObject(HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic.DATABUILD), parseService));
		
		return out;
	}

}
