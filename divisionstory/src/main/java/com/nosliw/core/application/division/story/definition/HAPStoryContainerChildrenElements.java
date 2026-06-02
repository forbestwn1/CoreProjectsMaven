package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public abstract class HAPStoryContainerChildrenElements extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.item.child.container";
	
	public static final String CONTAINERTYPE = "containerType";
	
	private String m_containerType;
	
	public HAPStoryContainerChildrenElements(String containerType) {
		this.m_containerType = containerType;
	}
	
	public String getContainerType() {
		return this.m_containerType;
	}

	abstract public HAPStoryContainerChildrenElements cloneContainer();
	
	public static HAPStoryContainerChildrenElements parseChildrenContainer(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPStoryContainerChildrenElements)parseService.parseEntityJSONImplicitAttribute(jsonObj, CONTAINERTYPE, PARSABLEENTITYDOMAIN);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CONTAINERTYPE, this.getContainerType());
	}
}

abstract class HAPStoryContainerChildrenElements__HAPEntityParsable extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryContainerChildrenElements element, HAPServiceParseEntity parseService) {
	}
	
	@Override
	public String getDomain() {   return HAPStoryContainerChildrenElements.PARSABLEENTITYDOMAIN;   }

}
