package com.nosliw.core.application.division.story.brick.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinitionWritableWithInit;
import com.nosliw.core.application.division.story.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;

public class HAPStoryElementVariable extends HAPStoryElement{

	private HAPDataDefinitionWritableWithInit m_dataDef;
	
	public HAPStoryElementVariable() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_VARIABLE));
	}

	public HAPStoryElementVariable(HAPDataDefinitionWritableWithInit dataDef) {
		this();
		this.m_dataDef = dataDef;
	}

	protected void cloneToStoryElement(HAPStoryElementVariable storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElement out = new HAPStoryElementVariable();
		this.cloneToStoryElement(out);
		return out;
	}

}
