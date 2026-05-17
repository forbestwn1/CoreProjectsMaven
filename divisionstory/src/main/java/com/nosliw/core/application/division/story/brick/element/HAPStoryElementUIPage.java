package com.nosliw.core.application.division.story.brick.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;

public class HAPStoryElementUIPage extends HAPStoryElement{

	public HAPStoryElementUIPage() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_PAGE));
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
