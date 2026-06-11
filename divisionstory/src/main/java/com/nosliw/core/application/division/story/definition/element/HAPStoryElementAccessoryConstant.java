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

public class HAPStoryElementAccessoryConstant extends HAPStoryElementAccessory implements HAPStoryElementWithEndPoint{

	public static final HAPStoryIdElementType TYPE = new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_CONSTANT);
	
	public HAPStoryElementAccessoryConstant() {
		this(null);
	}
	
	public HAPStoryElementAccessoryConstant(HAPEntityInfo entityInfo) {
		super(TYPE, entityInfo);
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryConstant storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementAccessoryConstant out = new HAPStoryElementAccessoryConstant();
		this.cloneToStoryElement(out);
		return out;
	}
}

@Component
class HAPStoryElementAccessoryConstant__HAPEntityParsable extends HAPStoryElementImpWithEntityInfoParser{

	@Override
	public String getSubName() {    return HAPStoryElementAccessoryConstant.TYPE.getElementType();    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementAccessoryConstant element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementAccessoryConstant out = new HAPStoryElementAccessoryConstant();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
