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
public class HAPStoryWizzardQuestionValueDataSourceResponseParmChooseIsShowDynamic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String ISSHOW = "isShow";

	private boolean m_isShow = false;
	
	public HAPStoryWizzardQuestionValueDataSourceResponseParmChooseIsShowDynamic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMISSHOW);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceResponseParmChooseIsShowDynamic(boolean isShow) {
		this();
		this.m_isShow = isShow;
	}
	
	public boolean getIsShow() {     return this.m_isShow;       }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		Boolean isShow = (Boolean)jsonObj.get(ISSHOW);
		if(isShow!=null) {
			this.m_isShow = isShow;
		}
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ISSHOW, this.m_isShow+"");
		typeJsonMap.put(ISSHOW, Boolean.class);
	}

}

@Component
class HAPStoryWizzardQuestionValueDataSourceResponseParmChooseIsShowDynamic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMISSHOW;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceResponseParmChooseIsShowDynamic out = new HAPStoryWizzardQuestionValueDataSourceResponseParmChooseIsShowDynamic();
		out.buildObject(obj, HAPSerializationFormat.JSON);
		return out;
	}

}

