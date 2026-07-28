package com.nosliw.core.application.common.event;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPIdBrickInBundle;

@HAPEntityWithAttribute
public class HAPEventEmitter extends HAPSerializableImp{

	@HAPAttribute
	public static final String EMITTERID = "emitterId";

	@HAPAttribute
	public static final String CHILDID = "childId";

	@HAPAttribute
	public static final String EVENTDEFINITION = "eventDefinition";

	private HAPIdBrickInBundle m_emitterBrickId;

	private String m_childId;
	
	private HAPEventDefinition m_eventDefinition;
	

	public HAPEventEmitter(HAPIdBrickInBundle emitterBrickId, String childId, HAPEventDefinition eventDefinition) {
		this.m_emitterBrickId = emitterBrickId;
		this.m_childId = childId;
		this.m_eventDefinition = eventDefinition;
	}

	public HAPIdBrickInBundle getEmitterBrickId() {     return this.m_emitterBrickId;       }
	public void setEmitterBrickId(HAPIdBrickInBundle emitterBrickId) {    this.m_emitterBrickId = emitterBrickId;        }
	
    public HAPEventDefinition getEventDefinition() {      return this.m_eventDefinition;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_emitterBrickId!=null) {
			jsonMap.put(EMITTERID, this.m_emitterBrickId.toStringValue(HAPSerializationFormat.JSON));
		}
		if(this.m_eventDefinition!=null) {
			jsonMap.put(EVENTDEFINITION, this.m_eventDefinition.toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(CHILDID, this.m_childId);
	}
}
