package com.nosliw.core.application.division.story.definition.element.ui;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryMetaDataChildElementUIInject extends HAPStoryMetaDataChildElementUI{

	public HAPStoryMetaDataChildElementUIInject() {
    	super(HAPConstantShared.STORY_CHILD_METADATA_TYPE_UIINJECT);
	}
	
}

@Component
class HAPStoryMetaDataChildElementUIInject_HAPEntityParsable extends HAPStoryMetaDataChildElementUI_HAPEntityParsable{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryMetaDataChildElementUIInject changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryMetaDataChildElementUIInject out = new HAPStoryMetaDataChildElementUIInject();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.STORY_CHILD_METADATA_TYPE_UIINJECT;   }

}
