package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementAccessoryConstant extends HAPStoryElementAccessory{

	public static final String CHILD_ENDPOINT = "endpoint";
	
	private HAPStoryIdElement m_endpointElementId;
	
	public HAPStoryElementAccessoryConstant() {
		this(null);
	}
	
	public HAPStoryElementAccessoryConstant(HAPEntityInfo entityInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_CONSTANT), entityInfo);
	}

	@Override
	public void addChild(HAPStoryElement ele, HAPPath path) {
		this.m_endpointElementId = ele.getElementId();
	}

	@Override
	public HAPStoryIdElement getChild(HAPPath path) {
		return this.m_endpointElementId;
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryConstant storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_endpointElementId = this.m_endpointElementId;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementAccessoryConstant out = new HAPStoryElementAccessoryConstant();
		this.cloneToStoryElement(out);
		return out;
	}
}
