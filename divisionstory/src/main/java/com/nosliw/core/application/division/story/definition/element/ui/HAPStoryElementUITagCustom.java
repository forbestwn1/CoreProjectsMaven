package com.nosliw.core.application.division.story.definition.element.ui;

import java.util.Map;

import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;

public class HAPStoryElementUITagCustom extends HAPStoryElementEntityComplex{

	public static final String CHILD_CONTENTWRAPPER = "contentwrapper";
	
	private HAPStoryIdElement m_contentWrapperElementId;

	private String m_uiTagId;
	
	private Map<String, String> m_attributes;
	
	public HAPStoryElementUITagCustom(String uiTagId, Map<String, String> attributes) {
		this.m_uiTagId = uiTagId;
		this.m_attributes.putAll(attributes);
		
		
	}
	
}
