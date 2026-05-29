package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.info.HAPEntityInfo;

//access element that attached to entity (command, event, variable, constant)
public abstract class HAPStoryElementAccessory extends HAPStoryElementImpWithEntityInfo{

	public HAPStoryElementAccessory(HAPStoryIdElementType elementType) {
		super(elementType);
	}

	public HAPStoryElementAccessory(HAPStoryIdElementType elementType, HAPEntityInfo entityInfo) {
		super(elementType, entityInfo);
	}

}
