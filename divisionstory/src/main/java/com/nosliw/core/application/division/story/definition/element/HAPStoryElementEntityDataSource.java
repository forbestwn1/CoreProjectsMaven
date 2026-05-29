package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfo;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithCommand;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityDataSource extends HAPStoryElementImpWithEntityInfo implements HAPStoryElementWithCommand{

	private String m_serviceId;
	
	public HAPStoryElementEntityDataSource() {
		this(null, null);
	}
	
	public HAPStoryElementEntityDataSource(String serviceId, HAPEntityInfo dataSourceInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_SERVICE), dataSourceInfo);
	}

	protected void cloneToStoryElement(HAPStoryElementEntityDataSource storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_serviceId = this.m_serviceId;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEntityDataSource out = new HAPStoryElementEntityDataSource();
		this.cloneToStoryElement(out);
		return out;
	}

}
