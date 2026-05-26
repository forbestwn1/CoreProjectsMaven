package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementAccessoryConstant extends HAPStoryElementAccessory{

	public static final String CHILD_ENDPOINT = "endpoint";
	
	public HAPStoryElementAccessoryConstant() {
		this(null);
	}
	
	public HAPStoryElementAccessoryConstant(HAPEntityInfo entityInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_CONSTANT), entityInfo);
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
