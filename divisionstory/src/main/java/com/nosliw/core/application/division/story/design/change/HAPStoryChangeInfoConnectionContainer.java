package com.nosliw.core.application.division.story.design.change;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPStoryChangeInfoConnectionContainer extends HAPStoryChangeInfoConnection{

	private HAPPath m_childPath;
	
    public HAPStoryChangeInfoConnectionContainer() {
    	super(HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN);
    }
	
    public HAPStoryChangeInfoConnectionContainer(HAPPath path) {
    	this();
    	this.m_childPath = path;
    }	
	
}
