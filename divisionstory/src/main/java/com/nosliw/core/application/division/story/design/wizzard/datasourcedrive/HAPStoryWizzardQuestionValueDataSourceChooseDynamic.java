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
public class HAPStoryWizzardQuestionValueDataSourceChooseDynamic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String DATASOURCEID = "dataSourceId";

	private String m_dataSource;
	
	public HAPStoryWizzardQuestionValueDataSourceChooseDynamic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEID);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceChooseDynamic(String dataSource) {
		this();
		this.m_dataSource = dataSource;
	}
	
	@Override
	public String getValueType() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEID;  }
	
	public String getDataSourceName() {     return this.m_dataSource;      }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_dataSource = (String)jsonObj.opt(DATASOURCEID);
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DATASOURCEID, this.m_dataSource);
	}

}

@Component
class HAPStoryWizzardQuestionChooseService_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEID;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceChooseDynamic out = new HAPStoryWizzardQuestionValueDataSourceChooseDynamic();
		out.buildObject(obj, HAPSerializationFormat.JSON);
		return out;
	}

}
