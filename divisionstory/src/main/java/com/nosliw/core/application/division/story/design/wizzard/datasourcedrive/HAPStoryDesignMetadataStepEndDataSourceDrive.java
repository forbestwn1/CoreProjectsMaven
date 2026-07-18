package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStepEnd;
import com.nosliw.core.application.division.story.design.HAPStoryParserEntityMetadataStep;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryDesignMetadataStepEndDataSourceDrive extends HAPStoryDesignMetadataStepEnd{

	@HAPAttribute
	public static final String URL = "url";
	
	private String m_url;
	
	
	public String getUrl() {    return this.m_url;     }
	
	public void setUrl(String url) {    this.m_url = url;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(URL, this.m_url);
	}
}

@Component
class HAPStoryDesignMetadataStepEnd_HAPEntityParsable extends HAPStoryParserEntityMetadataStep{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_END;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPStoryDesignMetadataStepEndDataSourceDrive out = new HAPStoryDesignMetadataStepEndDataSourceDrive();
		out.setUrl(jsonObj.getString(HAPStoryDesignMetadataStepEndDataSourceDrive.URL));
		return out;
	}

}
