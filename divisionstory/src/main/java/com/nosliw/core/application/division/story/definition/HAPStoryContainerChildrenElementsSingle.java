package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.utils.HAPConstantShared;

public class HAPStoryContainerChildrenElementsSingle extends HAPStoryContainerChildrenElements{

	private HAPStoryChildElement m_childElement;
	
	public HAPStoryContainerChildrenElementsSingle(HAPStoryChildElement childElement) {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_SINGLE);
		this.m_childElement = childElement;
	}

	public HAPStoryChildElement getChildElement() {     return this.m_childElement;      }

	@Override
	public HAPStoryContainerChildrenElements cloneContainer() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
