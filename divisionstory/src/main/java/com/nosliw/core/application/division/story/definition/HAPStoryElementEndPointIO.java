package com.nosliw.core.application.division.story.definition;

import java.util.Map;

public abstract class HAPStoryElementEndPointIO extends HAPStoryElement{

	public static final String IOTYPE = "IOType";
	
	private String m_IOType;
	
	public HAPStoryElementEndPointIO(HAPStoryIdElementType elementType, String IOType) {
		super(elementType);
		this.m_IOType = IOType;
	}
	
	public String getIOType() {     return this.m_IOType;     }
	public void setIOType(String ioType) {     this.m_IOType = ioType;        }

	protected void cloneToStoryElement(HAPStoryElementEndPointIO storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_IOType = this.m_IOType;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(IOTYPE, this.m_IOType);
	}
}
