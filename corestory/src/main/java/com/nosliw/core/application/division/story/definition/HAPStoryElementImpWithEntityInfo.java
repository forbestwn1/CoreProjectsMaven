package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.info.HAPWithEntityInfo;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;

//root class for all element
public abstract class HAPStoryElementImpWithEntityInfo extends HAPStoryElement implements HAPWithEntityInfo{

	private HAPEntityInfo m_entityInfo;

	public HAPStoryElementImpWithEntityInfo(HAPStoryIdElementType elementType) {
		this(elementType, null);
	}

	public HAPStoryElementImpWithEntityInfo(HAPStoryIdElementType elementType, HAPEntityInfo entityInfo) {
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
	public HAPEntityInfo getEntityInfo() {		return this.m_entityInfo;	}
	
	public void setEntityInfo(HAPEntityInfo entityInfo) {    this.m_entityInfo = entityInfo;      }

	protected void cloneToStoryElement(HAPStoryElementImpWithEntityInfo storyEle) {
		super.cloneToStoryElement(storyEle);
	    this.getEntityInfo().cloneToEntityInfo(storyEle.getEntityInfo());
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_entityInfo!=null) {
			jsonMap.put(HAPWithEntityInfo.ENTITYINFO, this.m_entityInfo.toStringValue(HAPSerializationFormat.JSON));
		}
	}

}
