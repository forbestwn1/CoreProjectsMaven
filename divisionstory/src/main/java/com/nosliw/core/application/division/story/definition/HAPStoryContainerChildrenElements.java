package com.nosliw.core.application.division.story.definition;

public abstract class HAPStoryContainerChildrenElements {

	private String m_containerType;
	
	public HAPStoryContainerChildrenElements(String containerType) {
		this.m_containerType = containerType;
	}
	
	public String getContainerType() {
		return this.m_containerType;
	}

	abstract public HAPStoryContainerChildrenElements cloneContainer();
		
}
