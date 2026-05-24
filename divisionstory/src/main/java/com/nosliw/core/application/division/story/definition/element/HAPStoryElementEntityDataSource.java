package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEntity;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityDataSource extends HAPStoryElementEntity{

	public static final String CHILD_COMMAND = "command";
	
	private String m_serviceId;
	
	private HAPStoryIdElement m_commandEleId;
	
	public HAPStoryElementEntityDataSource() {
		this(null);
	}
	
	public HAPStoryElementEntityDataSource(String serviceId) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_SERVICE));
		this.m_serviceId = serviceId;
	}

	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		this.m_commandEleId = ele.getElementId();
		return true;
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		return this.m_commandEleId;
	}

	protected void cloneToStoryElement(HAPStoryElementEntityDataSource storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_serviceId = this.m_serviceId;
		storyEle.m_commandEleId = this.m_commandEleId;
	}

	
	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEntityDataSource out = new HAPStoryElementEntityDataSource();
		this.cloneToStoryElement(out);
		return out;
	}

}