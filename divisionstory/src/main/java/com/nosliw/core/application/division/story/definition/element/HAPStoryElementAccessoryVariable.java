package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImp;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryReferenceElement;

public class HAPStoryElementAccessoryVariable extends HAPStoryElementAccessory{

	private HAPStoryReferenceElement m_endpointElement;
	
	public HAPStoryElementAccessoryVariable() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_VARIABLE));
	}

	public HAPStoryElementAccessoryVariable(HAPEntityInfo entityInfo) {
		this();
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryVariable storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElementImp cloneStoryElement() {
		HAPStoryElementImp out = new HAPStoryElementAccessoryVariable();
		this.cloneToStoryElement(out);
		return out;
	}

	@Override
	public void addChild(HAPStoryElement ele, HAPPath path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HAPStoryReferenceElement getChild(HAPPath path) {
		// TODO Auto-generated method stub
		return null;
	}

}
