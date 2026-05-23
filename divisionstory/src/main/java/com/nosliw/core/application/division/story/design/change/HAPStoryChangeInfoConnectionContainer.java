package com.nosliw.core.application.division.story.design.change;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPStoryChangeInfoConnectionContainer extends HAPStoryChangeInfoConnection{

	private HAPPath m_childPath;
	
	private Object m_metaData;
	
    public HAPStoryChangeInfoConnectionContainer() {
    	super(HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN);
    }
	
    public HAPStoryChangeInfoConnectionContainer(HAPPath path) {
    	this();
    	this.m_childPath = path;
    }	
	
    public HAPStoryChangeInfoConnectionContainer(HAPPath path, Object metaData) {
    	this();
    	this.m_childPath = path;
    }	
}
