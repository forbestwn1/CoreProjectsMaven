package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic extends HAPStoryWizzardValueInQuestionairImp{

	public static final String UITAGINFO = "uiTagInfo";
	
	private HAPStoryWizzardUITagInfo m_uiTagInfo;

	public HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMUITAG);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic(HAPStoryWizzardUITagInfo uiTagInfo) {
		this();
		this.m_uiTagInfo = uiTagInfo;
	}
	
	public HAPStoryWizzardUITagInfo getUITagInfo() {		return this.m_uiTagInfo;	}
	public void setUITagInfo(HAPStoryWizzardUITagInfo uiTagInfo) {      this.m_uiTagInfo = uiTagInfo;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_uiTagInfo!=null) {
			jsonMap.put(UITAGINFO, this.m_uiTagInfo.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMUITAG;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic out = new HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject uiTagInfoJsonObj = jsonObj.optJSONObject(HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic.UITAGINFO);
		if(uiTagInfoJsonObj!=null) {
			HAPStoryWizzardUITagInfo uiTagInfo = new HAPStoryWizzardUITagInfo();
			uiTagInfo.buildObject(uiTagInfoJsonObj, HAPSerializationFormat.JSON);
			out.setUITagInfo(uiTagInfo);
		}
		
		return out;
	}

}
