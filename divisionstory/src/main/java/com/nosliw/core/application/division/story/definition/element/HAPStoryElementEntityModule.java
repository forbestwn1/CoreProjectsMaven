package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityModule extends HAPStoryElementEntity{

	public HAPStoryElementEntityModule() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_MODULE));
	}

	@Override
	public void addChild(HAPStoryElement ele, HAPPath path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HAPStoryIdElement getChild(HAPPath path) {
		// TODO Auto-generated method stub
		return null;
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
