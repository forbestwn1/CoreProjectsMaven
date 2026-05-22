package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImp;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityDataSource extends HAPStoryElementImp{

	private String m_serviceId;
	
	private HAPInteractiveTask m_serviceInterface;

	private Map<String, HAPStoryIdElement> m_requestIOs;
	
	private Map<String, HAPStoryIdElement> m_responseIOs;
	
	
	public HAPStoryElementEntityDataSource(String serviceId, HAPInteractiveTask serviceInterface) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_SERVICE));
		this.m_serviceId = serviceId;
		this.m_serviceInterface = serviceInterface;
	}

	@Override
	public HAPStoryElementImp cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
