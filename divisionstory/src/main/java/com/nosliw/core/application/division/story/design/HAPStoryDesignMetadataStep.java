package com.nosliw.core.application.division.story.design;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public abstract class HAPStoryDesignMetadataStep extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.wizzard.metadata";
	
	@HAPAttribute
	public static final String TYPE = "type";
	
	private String m_type;
	
	public HAPStoryDesignMetadataStep(String type) {
		this.m_type = type;
	}
	
	public String getType() {
		return this.m_type;
	}

	public abstract void clear();

	public static HAPStoryDesignMetadataStep parseDesignMetadata(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPStoryDesignMetadataStep)parseService.parseEntityJSONImplicitAttribute(jsonObj, TYPE, PARSABLEENTITYDOMAIN);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
	}
}
