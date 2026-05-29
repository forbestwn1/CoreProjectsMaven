package com.nosliw.core.application.division.story.definition;

import java.util.List;

import com.nosliw.common.utils.HAPConstantShared;

//for element container 
public class HAPStoryContainerChildrenElementsList extends HAPStoryContainerChildrenElements{

	private List<HAPStoryContainerChildrenElements> m_childElement;
	
	public HAPStoryContainerChildrenElementsList() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST);
	}

    public HAPStoryContainerChildrenElements getChildContainer(int childIndex) {
    	return this.m_childElement.get(childIndex);
    }
    
    public HAPStoryContainerChildrenElements newChildContainer(HAPStoryContainerChildrenElements childContainer) {
    	this.m_childElement.add(childContainer);
    	return childContainer;
    }

	@Override
	public HAPStoryContainerChildrenElements cloneContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
