package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntityComplex;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityUIPage extends HAPStoryElementEntityComplex{

	public static final String CHILD_CONTENT = "content";
	
	private HAPStoryIdElement m_contentEleId;
	
	public HAPStoryElementEntityUIPage() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UIPAGE));
	}

	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		boolean out = super.addChild(ele, childName);
		if(out==false) {
			if(CHILD_CONTENT.equals(childName)) {
				this.m_contentEleId = ele.getElementId();
			}
		}
		return out;
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		HAPStoryIdElement out = super.getChild(childName);
		if(out==null) {
			if(CHILD_CONTENT.equals(childName)) {
				out = this.m_contentEleId;
			}
		}
		return out;
	}

	protected void cloneToStoryElement(HAPStoryElementEntityUIPage storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_contentEleId = this.m_contentEleId;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEntityUIPage out = new HAPStoryElementEntityUIPage();
		this.cloneToStoryElement(out);
		return out;
	}

}
