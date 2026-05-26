package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.info.HAPWithEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

public class HAPStoryElementEntityDataSource extends HAPStoryElement implements HAPWithEntityInfo{

	public static final String CHILD_COMMAND = "command";
	
	private String m_serviceId;
	
	private HAPEntityInfo m_entityInfo;
	
	public HAPStoryElementEntityDataSource() {
		this(null, null);
	}
	
	public HAPStoryElementEntityDataSource(String serviceId, HAPEntityInfo dataSourceInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_SERVICE));
		this.m_entityInfo = new HAPEntityInfoImp();
		this.m_serviceId = serviceId;
		dataSourceInfo.cloneToEntityInfo(this.m_entityInfo);
	}

	@Override
	public HAPEntityInfo getEntityInfo() {  return this.m_entityInfo;  }

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