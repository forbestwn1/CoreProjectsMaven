package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityUIContent extends HAPStoryElementEntity{

	private String m_html;
	
	private Map<String, HAPStoryIdElement> m_childrenElementId;
	
	public HAPStoryElementEntityUIContent(HAPStoryIdElementType elementType) {
		super(elementType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
