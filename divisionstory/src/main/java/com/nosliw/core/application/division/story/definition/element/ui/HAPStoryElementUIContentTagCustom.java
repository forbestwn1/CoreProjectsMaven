package com.nosliw.core.application.division.story.definition.element.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementParser;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementUIContentTagCustom extends HAPStoryElement{

	public static final String CHILD_CONTENTWRAPPER = "contentwrapper";
	
	public static final String UITAGID = "uiTagId";
	
	public static final String ATTRIBUTES = "attributes";
	
	private String m_uiTagId;
	
	private Map<String, String> m_attributes;
	
	public HAPStoryElementUIContentTagCustom() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UICONTENT_CUSTOMTAG));
		this.m_attributes = new LinkedHashMap<String, String>();
	}
	
	public HAPStoryElementUIContentTagCustom(String uiTagId, Map<String, String> attributes) {
		this();
		this.m_uiTagId = uiTagId;
		this.m_attributes.putAll(attributes);
	}

	public String getTagId() {   return this.m_uiTagId;   }
	void setUITagId(String uiTagId) {      this.m_uiTagId = uiTagId;      }
	
	public Map<String, String> getAttributes(){    return this.m_attributes;      }
	void addAttribute(String name, String value) {     this.m_attributes.put(name, value);        }
	
	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(UITAGID, this.m_uiTagId);
		jsonMap.put(ATTRIBUTES, HAPUtilityJson.buildMapJson(this.m_attributes));
	}
	
}

@Component
class HAPStoryElementUIContentTagCustom__HAPEntityParsable extends HAPStoryElementParser{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_UICONTENT_CUSTOMTAG;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementUIContentTagCustom element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		element.setUITagId(jsonObj.getString(HAPStoryElementUIContentTagCustom.UITAGID));
		
		JSONObject attrJsonObj = jsonObj.getJSONObject(HAPStoryElementUIContentTagCustom.ATTRIBUTES);
		for(Object key : attrJsonObj.keySet()) {
			String name = (String)key;
			element.addAttribute(name, attrJsonObj.getString(name));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementUIContentTagCustom out = new HAPStoryElementUIContentTagCustom();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
