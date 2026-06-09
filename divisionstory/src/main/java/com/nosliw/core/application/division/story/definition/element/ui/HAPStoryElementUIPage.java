package com.nosliw.core.application.division.story.definition.element.ui;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfo;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfoParser;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithVariable;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementUIPage extends HAPStoryElementImpWithEntityInfo{

	public static final String CHILD_CONTENTWRAPPER = "contentwrapper";
	
	public HAPStoryElementUIPage() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UIPAGE));
	}

	public HAPStoryElementUIPage(HAPEntityInfo entityInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UIPAGE), entityInfo);
	}

	public static HAPPath buildPathToVariableCollection() {
		return new HAPPath(HAPStoryElementUIPage.CHILD_CONTENTWRAPPER).appendSegment(HAPStoryElementWithVariable.CHILD_VARIABLE);
	}
	
	protected void cloneToStoryElement(HAPStoryElementUIPage storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementUIPage out = new HAPStoryElementUIPage();
		this.cloneToStoryElement(out);
		return out;
	}

}

@Component
class HAPStoryElementUIPage__HAPEntityParsable extends HAPStoryElementImpWithEntityInfoParser{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_UIPAGE;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementUIPage element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementUIPage out = new HAPStoryElementUIPage();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
