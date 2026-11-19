package com.nosliw.core.application.division.story.definition;

//connection between event and task
public abstract class HAPStoryElementContextRunnable extends HAPStoryElement{

	//element id to trigure task
	private HAPStoryReferenceElement m_baseElement;
	
	//task element id
	private HAPStoryReferenceElement m_runnable;
	
	public HAPStoryElementContextRunnable(HAPStoryIdElementType elementType) {
		super(elementType);
	}

}
