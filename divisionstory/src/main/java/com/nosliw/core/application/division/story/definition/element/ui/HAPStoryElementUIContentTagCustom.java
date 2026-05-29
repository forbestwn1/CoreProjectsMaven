package com.nosliw.core.application.division.story.definition.element.ui;

import java.util.Map;

import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;

public class HAPStoryElementUIContentTagCustom extends HAPStoryElementEntityComplex{

	public static final String CHILD_CONTENTWRAPPER = "contentwrapper";
	
	private String m_uiTagId;
	
	private Map<String, String> m_attributes;
	
	public HAPStoryElementUIContentTagCustom(String uiTagId, Map<String, String> attributes) {
		this.m_uiTagId = uiTagId;
		this.m_attributes.putAll(attributes);
		
		
	}
	
}
