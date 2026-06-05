package com.nosliw.core.application.division.story.definition;

public abstract class HAPStoryContainerChildrenElementsMultiple extends HAPStoryContainerChildrenElements{

	public HAPStoryContainerChildrenElementsMultiple(String containerType) {
		super(containerType);
	}
	
    abstract public HAPStoryContainerChildrenElements getChildContainer(String childName);

	abstract public HAPStoryContainerChildrenElementsWrapper removeChild(String childName);
	
}
