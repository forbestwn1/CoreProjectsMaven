package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinitionWritableWithInit;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementAccessoryVariable extends HAPStoryElementEntity{

	private HAPDataDefinitionWritableWithInit m_dataDef;
	
	public HAPStoryElementAccessoryVariable() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_VARIABLE));
	}

	public HAPStoryElementAccessoryVariable(HAPDataDefinitionWritableWithInit dataDef) {
		this();
		this.m_dataDef = dataDef;
	}

	protected void cloneToStoryElement(HAPStoryElementAccessoryVariable storyEle) {
		super.cloneToStoryElement(storyEle);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElement out = new HAPStoryElementAccessoryVariable();
		this.cloneToStoryElement(out);
		return out;
	}

}
