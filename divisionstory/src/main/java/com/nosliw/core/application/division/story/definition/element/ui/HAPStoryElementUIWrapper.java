package com.nosliw.core.application.division.story.definition.element.ui;

import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;

public class HAPStoryElementUIWrapper extends HAPStoryElementEntityComplex{

	public static final String CHILD_CONTENT = "content";
	
	private HAPStoryIdElement m_contentEleId;
	
	public HAPStoryElementUIWrapper() {
		super(null);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
