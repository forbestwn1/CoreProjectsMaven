package com.nosliw.core.application.common.event;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPEventProcess extends HAPSerializableImp{

	@HAPAttribute
	public static String EVENTEMITTER = "eventEmitter";
	
	@HAPAttribute
	public static String HANDLERREFERENCE = "handlerReference";
	
	private HAPEventEmitter m_eventEmmitter;
	
	private HAPEventHandlerReference m_handlerReference;
	
	
	public HAPEventProcess(HAPEventEmitter emitter, HAPEventHandlerReference handlerReference) {
		this.m_eventEmmitter = emitter;
		this.m_handlerReference = handlerReference;
	}
	
	public HAPEventEmitter getEventEmitter() {      return this.m_eventEmmitter;         }
	
	public HAPEventHandlerReference getEventHandlerReference() {      return this.m_handlerReference;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_eventEmmitter!=null) {
			jsonMap.put(EVENTEMITTER, this.m_eventEmmitter.toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(HANDLERREFERENCE, this.m_handlerReference.toStringValue(HAPSerializationFormat.JSON));
	}

}
