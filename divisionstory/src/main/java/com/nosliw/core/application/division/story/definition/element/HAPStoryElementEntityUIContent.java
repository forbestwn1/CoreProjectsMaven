package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImp;

public class HAPStoryElementEntityUIContent extends HAPStoryElementEntity{

	private String m_html;
	
	private Map<String, HAPStoryUIChild> m_children;
	
	public HAPStoryElementEntityUIContent() {
		super(null);
	}

	@Override
	public HAPStoryElementImp cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
