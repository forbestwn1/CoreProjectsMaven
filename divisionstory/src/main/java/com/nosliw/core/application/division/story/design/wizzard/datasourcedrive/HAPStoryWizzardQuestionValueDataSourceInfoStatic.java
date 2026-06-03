package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.application.entity.datasource.HAPServiceProfile;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	public static final String DATASOURCEINFO = "dataSourceInfo";
	
	private HAPServiceProfile m_dataSrouceProfile;
	
	public HAPStoryWizzardQuestionValueDataSourceInfoStatic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEINFO);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceInfoStatic(HAPServiceProfile dataSrouceProfile) {
		this();
		this.m_dataSrouceProfile = dataSrouceProfile;
	}
	
	public void setDataSourceProfile(HAPServiceProfile dataSrouceProfile) {      this.m_dataSrouceProfile = dataSrouceProfile;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_dataSrouceProfile!=null) {
			jsonMap.put(DATASOURCEINFO, this.m_dataSrouceProfile.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryWizzardQuestionValueDataSourceInfoStatic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEINFO;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceInfoStatic out = new HAPStoryWizzardQuestionValueDataSourceInfoStatic();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject dataSourceInfoJsonObj = jsonObj.optJSONObject(HAPStoryWizzardQuestionValueDataSourceInfoStatic.DATASOURCEINFO);
		if(dataSourceInfoJsonObj!=null) {
			out.setDataSourceProfile(HAPServiceProfile.parse(dataSourceInfoJsonObj, parseService));
		}
		
		return out;
	}

}
