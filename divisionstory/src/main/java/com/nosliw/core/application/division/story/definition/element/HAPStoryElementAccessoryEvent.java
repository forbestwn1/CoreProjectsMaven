package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementAccessoryEvent extends HAPStoryElementAccessory{

	public HAPStoryElementAccessoryEvent(HAPEntityInfo eventInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_COMMAND), eventInfo);
	}


	
	
	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
