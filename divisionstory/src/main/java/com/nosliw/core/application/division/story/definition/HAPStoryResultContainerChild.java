package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public class HAPStoryResultContainerChild {

	private HAPStoryContainerChildrenElements m_childContainer;
	
	private HAPPath m_remainingPath;
	
	
	public HAPStoryResultContainerChild(HAPStoryContainerChildrenElements childContainer, HAPPath remainingPath) {
		this.m_childContainer = childContainer;
		this.m_remainingPath = remainingPath;
	}
	
    public HAPPath getRemainingPath() {      return this.m_remainingPath;        }
	
    public HAPStoryContainerChildrenElements getChildContainer() {     return this.m_childContainer;      }
	
}
