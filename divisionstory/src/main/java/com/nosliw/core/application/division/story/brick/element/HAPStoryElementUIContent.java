package com.nosliw.core.application.division.story.brick.element;

import java.util.Map;

import com.nosliw.core.application.division.story.HAPStoryIdElement;
import com.nosliw.core.application.division.story.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;

public class HAPStoryElementUIContent extends HAPStoryElement{

	private String m_html;
	
	private Map<String, HAPStoryIdElement> m_childrenElementId;
	
	public HAPStoryElementUIContent(HAPStoryIdElementType elementType) {
		super(elementType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
