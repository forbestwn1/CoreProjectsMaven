package com.nosliw.core.application.division.story.definition.element.ui;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.core.application.division.story.definition.HAPStoryMetaDataChildElement;
import com.nosliw.core.application.division.story.definition.HAPStoryMetaDataChildElementParser;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public abstract class HAPStoryMetaDataChildElementUI extends HAPStoryMetaDataChildElement{

	public static final String SLOTNAME = "slotName";
	
    private String m_slotName;

    public HAPStoryMetaDataChildElementUI(String type) {
    	super(type);
    }
    
    public HAPStoryMetaDataChildElementUI(String type, String slotName) {
    	this(type);
    	this.m_slotName = slotName;
    }

    public String getSlotName() {    return this.m_slotName;      }
    public void setSlotName(String slotname) {      this.m_slotName = slotname;       }
    
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(SLOTNAME, this.getSlotName());
	}
    
}

abstract class HAPStoryMetaDataChildElementUI_HAPEntityParsable extends HAPStoryMetaDataChildElementParser{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryMetaDataChildElementUI changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
		changeItem.setSlotName((String)jsonObj.opt(HAPStoryMetaDataChildElementUI.SLOTNAME));
	}

}
