package com.nosliw.core.application.division.story.brick.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;
import com.nosliw.core.data.HAPData;

public class HAPStoryElementConstant extends HAPStoryElement{

	private HAPData m_data;
	
	public HAPStoryElementConstant() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_CONSTANT));
	}

	public HAPStoryElementConstant(HAPData data) {
		this();
		this.m_data = data;
	}

	protected void cloneToStoryElement(HAPStoryElementConstant storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElement out = new HAPStoryElementConstant();
		this.cloneToStoryElement(out);
		return out;
	}

}
