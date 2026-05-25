package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementAccessoryVariable extends HAPStoryElementAccessory{

	public static final String CHILD_ENDPOINT = "endpoint";
	
	private HAPStoryIdElement m_endpointElementId;
	
	public HAPStoryElementAccessoryVariable() {
		this(null);
	}
	
	public HAPStoryElementAccessoryVariable(HAPEntityInfo entityInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_CONSTANT), entityInfo);
	}

	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		this.m_endpointElementId = ele.getElementId();
		return true;
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		return this.m_endpointElementId;
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryVariable storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_endpointElementId = this.m_endpointElementId;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementAccessoryVariable out = new HAPStoryElementAccessoryVariable();
		this.cloneToStoryElement(out);
		return out;
	}

}
