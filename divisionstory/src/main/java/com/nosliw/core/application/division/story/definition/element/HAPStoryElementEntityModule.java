package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityStory;

public class HAPStoryElementEntityModule extends HAPStoryElementEntityComplex{

	public static final String CHILD_PAGE = "page";
	
	public HAPStoryElementEntityModule() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_MODULE));
	}

	public static HAPPath getAddPageChildPath() {	   return HAPStoryUtilityStory.buildChildPathForElement(new HAPPath(CHILD_PAGE));   }

	
	
	protected void cloneToStoryElement(HAPStoryElementEntityModule storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEntityModule out = new HAPStoryElementEntityModule();
		this.cloneToStoryElement(out);
		return out;
	}

}
