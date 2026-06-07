package com.nosliw.core.application.division.story.definition.element.ui;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryMetaDataChildElementUIAppend extends HAPStoryMetaDataChildElementUI{

    public HAPStoryMetaDataChildElementUIAppend(String slotName) {
    	this();
    	this.setSlotName(slotName);
    }
	
    HAPStoryMetaDataChildElementUIAppend() {
    	super(HAPConstantShared.STORY_CHILD_METADATA_TYPE_UIAPPEND);
    }
	
}

@Component
class HAPStoryMetaDataChildElementUIAppend_HAPEntityParsable extends HAPStoryMetaDataChildElementUI_HAPEntityParsable{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryMetaDataChildElementUIAppend changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryMetaDataChildElementUIAppend out = new HAPStoryMetaDataChildElementUIAppend();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.STORY_CHILD_METADATA_TYPE_UIAPPEND;   }

}
