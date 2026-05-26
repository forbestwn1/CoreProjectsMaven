package com.nosliw.core.application.division.story.definition;

public abstract class HAPStoryElementEndPointIO extends HAPStoryElement{

	private String m_IOType;
	
	public HAPStoryElementEndPointIO(HAPStoryIdElementType elementType, String IOType) {
		super(elementType);
		this.m_IOType = IOType;
	}
	
	public String getIOType() {     return this.m_IOType;     }

	protected void cloneToStoryElement(HAPStoryElementEndPointIO storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_IOType = this.m_IOType;
	}
}
