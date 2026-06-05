package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryChangeItemDelete extends HAPStoryChangeItemModifyElement{

	public HAPStoryChangeItemDelete() {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_DELETE);
	}
	
	public HAPStoryChangeItemDelete(HAPStoryIdElement targetElementId) {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_DELETE, targetElementId);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}
}


@Component
class HAPStoryChangeItemDelete_HAPEntityParsable extends HAPStoryChangeItemModifyElement_HAPEntityParsable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_CHANGETYPE_DELETE;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryChangeItemDelete out = new HAPStoryChangeItemDelete();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemDelete changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
	}
}

