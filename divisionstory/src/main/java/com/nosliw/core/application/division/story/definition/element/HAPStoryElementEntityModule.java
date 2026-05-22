package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImp;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityModule extends HAPStoryElementEntity{

	public HAPStoryElementEntityModule() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_MODULE));
	}

	protected void cloneToStoryElement(HAPStoryElementEntityModule storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElementImp cloneStoryElement() {
		HAPStoryElementImp out = new HAPStoryElementEntityModule();
		this.cloneToStoryElement(out);
		return out;
	}

}
