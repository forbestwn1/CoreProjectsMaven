package com.nosliw.core.application.division.story.definition.element.ui;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryElementParser;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementUIWrapperContent extends HAPStoryElementEntityComplex{

	public static final String CHILD_CONTENT = "content";
	
	public HAPStoryElementUIWrapperContent() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UIWRAPPER));
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}

@Component
class HAPStoryElementUIWrapperContent__HAPEntityParsable extends HAPStoryElementParser{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_UIWRAPPER;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementUIWrapperContent element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementUIWrapperContent out = new HAPStoryElementUIWrapperContent();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

