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
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPParserCriteriaImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String DATATYPECRITERIA = "dataTypeCriteria";
	
	@HAPAttribute
	public static final String DATANAME = "dataName";
	
	private HAPDataTypeCriteria m_dataTypeCriteria;
	
	private String m_dataName;

	public HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEDATACRITERIAINFO);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic(HAPDataTypeCriteria dataTypeCriteria, String dataName) {
		this();
		this.m_dataTypeCriteria = dataTypeCriteria;
		this.m_dataName = dataName;
	}
	
	public void setDataTypeCriteria(HAPDataTypeCriteria dataTypeCriteria) {     this.m_dataTypeCriteria = dataTypeCriteria;       }
	
	public String getDataName() {      return this.m_dataName;      }
	public void setDataName(String dataName) {     this.m_dataName = dataName;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DATATYPECRITERIA, this.m_dataTypeCriteria.toStringValue(HAPSerializationFormat.LITERATE));
		jsonMap.put(DATANAME, this.m_dataName);
	}

}

@Component
class HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEDATACRITERIAINFO;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic out = new HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		String dataCriteriaDefObj = (String)jsonObj.opt(HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic.DATATYPECRITERIA);
		if(dataCriteriaDefObj!=null) {
			out.setDataTypeCriteria(HAPParserCriteriaImp.getInstance().parseCriteria(dataCriteriaDefObj));
		}
		
		out.setDataName((String)jsonObj.opt(HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic.DATANAME));
		
		return out;
	}
}
