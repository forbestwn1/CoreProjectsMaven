package com.nosliw.core.application.division.story.definition;

public class HAPStoryChildElement {

	private HAPStoryIdElement m_elementId;
	
	private Object m_metaData;
	
	public HAPStoryChildElement(HAPStoryIdElement elementId, Object metaData) {
		this.m_elementId = elementId;
		this.m_metaData = metaData;
	}
	
	public HAPStoryIdElement getElementId() {    return this.m_elementId;     }
	
	public Object getMetaData() {    return this.m_metaData;      }

}
