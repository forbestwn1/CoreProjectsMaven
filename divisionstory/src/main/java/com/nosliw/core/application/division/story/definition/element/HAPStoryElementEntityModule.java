package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityModule extends HAPStoryElementEntityComplex{

	public HAPStoryElementEntityModule() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_MODULE));
	}

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
