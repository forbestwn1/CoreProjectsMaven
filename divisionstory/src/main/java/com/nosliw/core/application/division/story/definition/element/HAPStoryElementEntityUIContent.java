package com.nosliw.core.application.division.story.definition.element;

import java.util.List;

import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;

public class HAPStoryElementEntityUIContent extends HAPStoryElementEntity{

	private String m_html;
	
	private List<HAPStoryUIChildContent> m_children;
	
	public HAPStoryElementEntityUIContent() {
		super(null);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
