package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public class HAPStoryResultElementChild {

	private HAPStoryChildElement m_childElement;
	
	private HAPPath m_remainingPath;
	
	
	public HAPStoryResultElementChild(HAPStoryChildElement childElement, HAPPath remainingPath) {
		this.m_childElement = childElement;
		this.m_remainingPath = remainingPath;
	}
	
    public HAPPath getRemainingPath() {      return this.m_remainingPath;        }
	
    public HAPStoryChildElement getChildElement() {     return this.m_childElement;      }
	
}
