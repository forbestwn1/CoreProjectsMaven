package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;

//for attribute
public class HAPStoryContainerChildrenElementsMap extends HAPStoryContainerChildrenElements{

	private Map<String, HAPStoryContainerChildrenElements> m_childElement;
	
	public HAPStoryContainerChildrenElementsMap() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_MAP);
	}

	public Map<String, HAPStoryContainerChildrenElements> getChildrenContainer(){
		return this.m_childElement;
	}
	
    public HAPStoryContainerChildrenElements getChildContainer(String childName) {
    	return this.m_childElement.get(childName);
    }
    
    public HAPStoryContainerChildrenElements newChildContainer(String childName, HAPStoryContainerChildrenElements childContainer) {
    	this.m_childElement.put(childName, childContainer);
    	return childContainer;
    }

	@Override
	public HAPStoryContainerChildrenElements cloneContainer() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
