package com.nosliw.core.application.division.story.definition.element.ui;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfo;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementUIPage extends HAPStoryElementImpWithEntityInfo{

	public static final String CHILD_CONTENTWRAPPER = "contentwrapper";
	
	public HAPStoryElementUIPage() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UIPAGE));
	}

	public HAPStoryElementUIPage(HAPEntityInfo entityInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UIPAGE), entityInfo);
	}

	protected void cloneToStoryElement(HAPStoryElementUIPage storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementUIPage out = new HAPStoryElementUIPage();
		this.cloneToStoryElement(out);
		return out;
	}

}
