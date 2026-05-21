package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityUIPage extends HAPStoryElementEntity{

	private HAPStoryIdElement m_contentEleId;
	
	public HAPStoryElementEntityUIPage() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_PAGE));
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
