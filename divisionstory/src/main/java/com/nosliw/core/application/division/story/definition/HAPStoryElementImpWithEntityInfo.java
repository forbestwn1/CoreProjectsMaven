package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;

public abstract class HAPStoryElementImpWithEntityInfo extends HAPEntityInfoImp implements HAPStoryElement{

	private HAPStoryIdElement m_id;
	
	private HAPStoryIdElementType m_elementType; 
	
	public HAPStoryElementImpWithEntityInfo(HAPStoryIdElementType elementType) {
		this.m_elementType = elementType;
	}

	public HAPStoryElementImpWithEntityInfo(HAPStoryIdElementType elementType, HAPEntityInfo entityInfo) {
		this(elementType);
		if(entityInfo!=null) {
			entityInfo.cloneToEntityInfo(this);
		}
	}

	@Override
	public HAPStoryIdElement getElementId() {	return this.m_id;	}
	public void setElementId(HAPStoryIdElement elementId) {    this.m_id = elementId;       }
	
	@Override
	public HAPStoryIdElementType getElementType() {     return this.m_elementType;       }
	protected void setElementType(HAPStoryIdElementType elementType) {    this.m_elementType = elementType;     }
	
	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		throw new RuntimeException();
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		return null;
	}
	
	protected void cloneToStoryElement(HAPStoryElementImpWithEntityInfo storyEle) {
		if(this.m_id!=null) {
			storyEle.setElementId((HAPStoryIdElement)m_id.cloneElementReference());
		}
		storyEle.setElementType(this.m_elementType);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENTTYPE, this.m_elementType.getKey());
		if(this.m_id!=null) {
			jsonMap.put(ELEMENTID, this.m_id.getKey());
		}
	}

}
