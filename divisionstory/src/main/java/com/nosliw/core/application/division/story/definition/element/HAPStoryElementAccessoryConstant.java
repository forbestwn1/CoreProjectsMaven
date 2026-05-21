package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.data.HAPData;

public class HAPStoryElementAccessoryConstant extends HAPStoryElementEntity{

	private HAPData m_data;
	
	public HAPStoryElementAccessoryConstant() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_CONSTANT));
	}

	public HAPStoryElementAccessoryConstant(HAPData data) {
		this();
		this.m_data = data;
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryConstant storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElement out = new HAPStoryElementAccessoryConstant();
		this.cloneToStoryElement(out);
		return out;
	}

}
