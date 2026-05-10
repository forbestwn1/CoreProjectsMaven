package com.nosliw.core.application.division.story.brick.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;

public class HAPStoryElementModule extends HAPStoryElement{

	public HAPStoryElementModule() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_MODULE));
	}

	protected void cloneToStoryElement(HAPStoryElementModule storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElement out = new HAPStoryElementModule();
		this.cloneToStoryElement(out);
		return out;
	}

}
