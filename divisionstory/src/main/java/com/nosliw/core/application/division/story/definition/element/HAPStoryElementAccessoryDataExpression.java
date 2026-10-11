package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfoParser;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementAccessoryDataExpression extends HAPStoryElementAccessory implements HAPStoryElementWithEndPoint{

	public static final HAPStoryIdElementType TYPE = new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_DATAEXPRESSION);
	
	public HAPStoryElementAccessoryDataExpression() {
		this(null);
	}
	
	public HAPStoryElementAccessoryDataExpression(HAPEntityInfo entityInfo) {
		super(TYPE, entityInfo);
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryDataExpression storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementAccessoryDataExpression out = new HAPStoryElementAccessoryDataExpression();
		this.cloneToStoryElement(out);
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}
	
}

@Component
class HAPStoryElementAccessoryDataExpression__HAPEntityParsable extends HAPStoryElementImpWithEntityInfoParser{

	@Override
	public String getSubName() {    return HAPStoryElementAccessoryDataExpression.TYPE.getElementType();    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementAccessoryDataExpression element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementAccessoryDataExpression out = new HAPStoryElementAccessoryDataExpression();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
