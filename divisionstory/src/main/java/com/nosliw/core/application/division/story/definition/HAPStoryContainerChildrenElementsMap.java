package com.nosliw.core.application.division.story.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;

//for attribute
public class HAPStoryContainerChildrenElementsMap extends HAPStoryContainerChildrenElementsCollection{

	private Map<String, HAPStoryContainerChildrenElements> m_childElement;
	
	public HAPStoryContainerChildrenElementsMap() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_MAP);
		this.m_childElement = new LinkedHashMap<String, HAPStoryContainerChildrenElements>();
	}

	public Map<String, HAPStoryContainerChildrenElements> getChildrenContainer(){
		return this.m_childElement;
	}
	
    @Override
	public HAPStoryContainerChildrenElements getChildContainer(String childName) {
    	return this.m_childElement.get(childName);
    }
    
    public HAPStoryContainerChildrenElements newChildContainer(String childName, HAPStoryContainerChildrenElements childContainer) {
    	this.m_childElement.put(childName, childContainer);
    	return childContainer;
    }

    @Override
	public HAPStoryContainerChildrenElementsSingle removeChild(String childName) {
    	return (HAPStoryContainerChildrenElementsSingle)this.m_childElement.remove(childName);
    }
    
	@Override
	public HAPStoryContainerChildrenElements cloneContainer() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
