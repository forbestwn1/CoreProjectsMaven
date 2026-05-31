package com.nosliw.core.application.division.story.definition;

public abstract class HAPStoryContainerChildrenElementsCollection extends HAPStoryContainerChildrenElements{

	public HAPStoryContainerChildrenElementsCollection(String containerType) {
		super(containerType);
	}
	
    abstract public HAPStoryContainerChildrenElements getChildContainer(String childName);

	abstract public HAPStoryContainerChildrenElementsSingle removeChild(String childName);
	
}
