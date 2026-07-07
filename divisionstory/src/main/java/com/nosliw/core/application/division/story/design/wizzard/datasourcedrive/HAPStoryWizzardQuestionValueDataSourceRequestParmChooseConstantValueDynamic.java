package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String CONSTANTDATA = "constantData";
	
	@HAPAttribute
	public static final String CONSTANTDATA_MULTIPLE = "constantDataMultiple";

	private HAPData m_constantData;
	
	private List<HAPData> m_constantDatas;

	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic(HAPData constantData) {
		this();
		this.m_constantData = constantData;
	}
	
	public HAPData getConstantData() {    return this.m_constantData;     }
	public void setConstantData(HAPData data) {    this.m_constantData = data;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_constantData!=null) {
			jsonMap.put(CONSTANTDATA, this.m_constantData.toStringValue(HAPSerializationFormat.JSON));
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
		
		Object dataConstantObj = jsonObj.opt(HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic.CONSTANTDATA);
		if(dataConstantObj!=null) {
			out.setConstantData(HAPUtilityData.buildDataWrapperFromObject(dataConstantObj));
		}
		return out;
	}

}
