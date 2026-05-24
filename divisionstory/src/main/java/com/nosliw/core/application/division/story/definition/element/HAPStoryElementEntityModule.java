package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityModule extends HAPStoryElementEntityComplex{

	public HAPStoryElementEntityModule() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_MODULE));
	}

	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		boolean out = super.addChild(ele, childName);
		if(out==false) {
			
		}
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		HAPStoryIdElement out = super.getChild(childName);
		if(out==null) {
			
		}
		return out;
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
