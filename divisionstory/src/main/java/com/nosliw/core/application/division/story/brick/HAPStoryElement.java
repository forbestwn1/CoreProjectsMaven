package com.nosliw.core.application.division.story.brick;

import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.application.division.story.HAPStoryIdElement;
import com.nosliw.core.application.division.story.HAPStoryIdElementType;

//element represent 
// standalone stuff.   module, page, 
@HAPEntityWithAttribute
public abstract class HAPStoryElement extends HAPSerializableImp{

	@HAPAttribute
	public static final String ELEMENTID = "elementId";
	
	@HAPAttribute
	public static final String ELEMENTTYPE = "elementType";
	
	
	private HAPStoryIdElement m_id;
	
	private HAPStoryIdElementType m_elementType; 
	
	//element may container children element
	private Set<HAPStoryContainerElement> m_children;
	
	private HAPStoryContainer<HAPStoryVariable> m_state;

	
	//element may have commands in order to receive from external 
	private HAPStoryContainer<HAPStoryCommand> m_commands;
	
	//element may have events in order to send something to external
	private HAPStoryContainer<HAPStoryEvent> m_events;

	
	public HAPStoryElement(HAPStoryIdElementType elementType) {
		this.m_elementType = elementType;
	}
	
	public void addChild(HAPStoryElement ele) {
		
	}
	
	
	public HAPStoryIdElement getElementId() {	return this.m_id;	}
	public void setElementId(HAPStoryIdElement elementId) {    this.m_id = elementId;       }
	
	public HAPStoryIdElementType getElementType() {     return this.m_elementType;       }
	protected void setElementType(HAPStoryIdElementType elementType) {    this.m_elementType = elementType;     }
	
	public abstract HAPStoryElement cloneStoryElement();
	
	protected void cloneToStoryElement(HAPStoryElement storyEle) {
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
