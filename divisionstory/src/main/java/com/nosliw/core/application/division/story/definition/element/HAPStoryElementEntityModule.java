package com.nosliw.core.application.division.story.definition.element;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryElementParser;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityElement;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementEntityModule extends HAPStoryElementEntityComplex{

	public static final String CHILD_PAGE = "page";
	
	public HAPStoryElementEntityModule() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_MODULE));
	}

	public static HAPPath getAddPageChildPath() {	   return HAPStoryUtilityElement.buildChildPathForElement(new HAPPath(CHILD_PAGE));   }

	
	
	protected void cloneToStoryElement(HAPStoryElementEntityModule storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEntityModule out = new HAPStoryElementEntityModule();
		this.cloneToStoryElement(out);
		return out;
	}

}


@Component
class HAPStoryElementEntityModule__HAPEntityParsable extends HAPStoryElementParser{

	@Override
	public String getSubName() {      return HAPConstantShared.STORYNODE_TYPE_MODULE;      }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementEntityModule element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementEntityModule out = new HAPStoryElementEntityModule();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
