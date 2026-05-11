package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionChooseService extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static final String PARSABLEENTITYTYPE = "story.wizzard.question.choolsservice";
	
	@HAPAttribute
	public static final String DATASOURCE = "serviceName";

	private String m_dataSource;
	
	public HAPStoryWizzardQuestionChooseService() {
		
	}
	
	public HAPStoryWizzardQuestionChooseService(String dataSource) {
		this.m_dataSource = dataSource;
	}
	
	public String getServiceName() {     return this.m_dataSource;      }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_dataSource = (String)jsonObj.opt(DATASOURCE);
		
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DATASOURCE, this.m_dataSource);
	}
	
}

@Component
class HAPStoryWizzardQuestionChooseService_HAPEntityParsable implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPStoryWizzardQuestionChooseService.PARSABLEENTITYTYPE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionChooseService out = new HAPStoryWizzardQuestionChooseService();
		out.buildObject(obj, HAPSerializationFormat.JSON);
		return out;
	}

}

