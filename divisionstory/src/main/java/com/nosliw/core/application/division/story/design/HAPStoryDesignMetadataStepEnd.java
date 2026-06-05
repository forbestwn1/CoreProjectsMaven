package com.nosliw.core.application.division.story.design;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryDesignMetadataStepEnd extends HAPStoryDesignMetadataStep{

	public static final String METADATA = "metaData";
	
	private HAPSerializable m_metaData;
	
	public HAPStoryDesignMetadataStepEnd() {
		super(HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_END);
	}

	public HAPStoryDesignMetadataStepEnd(HAPSerializable metaData) {
		this();
		this.m_metaData = metaData;
	}
	
	public void setMetaData(HAPSerializable metaData) {
		this.m_metaData = metaData;
	}

	@Override
	public void clear() {
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_metaData!=null) {
			jsonMap.put(METADATA, this.m_metaData.toStringValue(HAPSerializationFormat.JSON));
		}
	}

}

@Component
class HAPStoryDesignMetadataStepEnd_HAPEntityParsable extends HAPStoryParserEntityMetadataStep{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_END;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPStoryDesignMetadataStepEnd out = new HAPStoryDesignMetadataStepEnd();
		
		JSONObject metaDataJsonObj = jsonObj.optJSONObject(HAPStoryDesignMetadataStepEnd.METADATA);
		if(metaDataJsonObj!=null) {
			
		}
		
		return out;
	}

}
