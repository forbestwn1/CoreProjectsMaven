package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIO;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEndPointIOVariable extends HAPStoryElementEndPointIO{

	//
	private HAPDataDefinition m_dataDefinition;
	
	public HAPStoryElementEndPointIOVariable() {
		this(null, null);
	}
	
	public HAPStoryElementEndPointIOVariable(HAPDataDefinition dataDefinition, String IOType) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_ENDPOINT_VARIABLE), IOType);
		this.m_dataDefinition = dataDefinition;
	}

	@Override
	public void addChild(HAPStoryElement ele, HAPPath path) {
		throw new RuntimeException();
	}

	@Override
	public HAPStoryIdElement getChild(HAPPath path) {
		return null;
	}

	protected void cloneToStoryElement(HAPStoryElementEndPointIOVariable storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_dataDefinition = this.m_dataDefinition;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEndPointIOVariable out = new HAPStoryElementEndPointIOVariable();
		this.cloneToStoryElement(out);
		return out;
	}
}
