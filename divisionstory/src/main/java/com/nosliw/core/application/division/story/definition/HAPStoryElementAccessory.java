package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.info.HAPWithEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;

//access element that attached to entity (command, event, variable, constant)
public abstract class HAPStoryElementAccessory extends HAPStoryElement implements HAPWithEntityInfo{

	private HAPEntityInfo m_entityInfo;
	
	public HAPStoryElementAccessory(HAPStoryIdElementType elementType) {
		this(elementType, null);
	}

	public HAPStoryElementAccessory(HAPStoryIdElementType elementType, HAPEntityInfo entityInfo) {
		super(elementType);
		this.m_entityInfo = new HAPEntityInfoImp();
		if(entityInfo!=null) {
			entityInfo.cloneToEntityInfo(this.m_entityInfo);
		}
		else {
			this.m_entityInfo.setName(HAPConstantShared.NAME_DEFAULT);
		}
	}

	@Override
	public HAPEntityInfo getEntityInfo() {
		return this.m_entityInfo;
	}

}
