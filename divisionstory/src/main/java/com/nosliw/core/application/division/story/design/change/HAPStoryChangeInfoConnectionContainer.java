package com.nosliw.core.application.division.story.design.change;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPStoryChangeInfoConnectionContainer extends HAPStoryChangeInfoConnection{

	private HAPPath m_childPath;
	
	private Object m_metaData;
	
    public HAPStoryChangeInfoConnectionContainer(HAPPath path) {
    	this(path, null);
    }	
	
    public HAPStoryChangeInfoConnectionContainer(HAPPath path, Object metaData) {
    	super(HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN);
    	this.m_childPath = path;
    	this.m_metaData = metaData;
    }	
}
