package com.nosliw.core.application.division.story.definition.element.ui;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfo;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementUIPage extends HAPStoryElementImpWithEntityInfo{

	public static final String CHILD_CONTENTWRAPPER = "contentwrapper";
	
	private HAPStoryIdElement m_contentWrapperEleId;
	
	public HAPStoryElementUIPage() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UIPAGE));
	}

	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		boolean out = super.addChild(ele, childName);
		if(out==false) {
			if(CHILD_CONTENTWRAPPER.equals(childName)) {
				this.m_contentWrapperEleId = ele.getElementId();
			}
		}
		return out;
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		HAPStoryIdElement out = super.getChild(childName);
		if(out==null) {
			if(CHILD_CONTENTWRAPPER.equals(childName)) {
				out = this.m_contentWrapperEleId;
			}
		}
		return out;
	}

	protected void cloneToStoryElement(HAPStoryElementUIPage storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_contentWrapperEleId = this.m_contentWrapperEleId;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementUIPage out = new HAPStoryElementUIPage();
		this.cloneToStoryElement(out);
		return out;
	}

}
