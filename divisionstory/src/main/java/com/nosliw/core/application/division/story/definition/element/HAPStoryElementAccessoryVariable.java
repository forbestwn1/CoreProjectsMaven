package com.nosliw.core.application.division.story.definition.element;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfoParser;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementAccessoryVariable extends HAPStoryElementAccessory implements HAPStoryElementWithEndPoint{

	public static final HAPStoryIdElementType TYPE = new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_VARIABLE);
	
	public HAPStoryElementAccessoryVariable() {
		this(null);
	}
	
	public HAPStoryElementAccessoryVariable(HAPEntityInfo entityInfo) {
		super(TYPE, entityInfo);
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryVariable storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementAccessoryVariable out = new HAPStoryElementAccessoryVariable();
		this.cloneToStoryElement(out);
		return out;
	}

}

@Component
class HAPStoryElementAccessoryVariable__HAPEntityParsable extends HAPStoryElementImpWithEntityInfoParser{

	@Override
	public String getSubName() {    return HAPStoryElementAccessoryVariable.TYPE.getElementType();    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementAccessoryVariable element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementAccessoryVariable out = new HAPStoryElementAccessoryVariable();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
