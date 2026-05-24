package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIO;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.data.HAPData;

public class HAPStoryElementEndPointIOConstant extends HAPStoryElementEndPointIO{

	//constant value
	private HAPData m_data;
	
	public HAPStoryElementEndPointIOConstant() {
		this(null);
	}
	
	public HAPStoryElementEndPointIOConstant(HAPData data) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_ENDPOINT_CONSTANT), HAPConstantShared.DATAFLOW_OUT);
		this.m_data = data;
	}

	public HAPData getData() {    return this.m_data;      }
	
	protected void cloneToStoryElement(HAPStoryElementEndPointIOConstant storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_data = this.m_data;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEndPointIOConstant out = new HAPStoryElementEndPointIOConstant();
		this.cloneToStoryElement(out);
		return out;
	}
}
