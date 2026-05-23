package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;

public class HAPStoryElementAccessoryEvent extends HAPStoryElementAccessory{

	private HAPEntityInfo m_entityInfo;

	private HAPStoryIdElement m_taskElementId;
	
	private HAPStoryIdElement m_ioEndpointElementId;
	
	public HAPStoryElementAccessoryEvent(HAPEntityInfo entityInfo) {
		super();
		this.m_entityInfo = entityInfo;
		
		
	}

	public static HAPPath buildPathForRequestEndPoint(String parName) {}
	
	
	
	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
