package com.nosliw.core.application.common.event;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPEventProcess extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static String EVENTEMITTER = "eventEmitter";
	
	@HAPAttribute
	public static String HANDLERREFERENCE = "handlerReference";
	
	private HAPEventEmitter m_eventEmmitter;
	
	private HAPEventHandlerReference m_handlerReference;
	
	public HAPEventProcess() {}
	
	public HAPEventProcess(HAPEventEmitter emitter, HAPEventHandlerReference handlerReference) {
		this.m_eventEmmitter = emitter;
		this.m_handlerReference = handlerReference;
	}
	
	public HAPEventEmitter getEventEmitter() {      return this.m_eventEmmitter;         }
	public void setEventEmitter(HAPEventEmitter eventEmitter) {       this.m_eventEmmitter = eventEmitter;       }
	
	public HAPEventHandlerReference getEventHandlerReference() {      return this.m_handlerReference;       }
	public void setEventHandlerReference(HAPEventHandlerReference handler) {     this.m_handlerReference = handler;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_eventEmmitter!=null) {
			jsonMap.put(EVENTEMITTER, this.m_eventEmmitter.toStringValue(HAPSerializationFormat.JSON));
		}
		if(m_handlerReference!=null) {
			jsonMap.put(HANDLERREFERENCE, this.m_handlerReference.toStringValue(HAPSerializationFormat.JSON));
		}
	}

}

@Component
class HAPEventProcess_Parser implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPEventProcess.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPEventProcess out = new HAPEventProcess();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		out.setEventEmitter((HAPEventEmitter)parseService.parseEntityJSONExplicit(jsonObj.optJSONObject(HAPEventProcess.EVENTEMITTER), HAPEventEmitter.class.getName()));
		
		out.setEventHandlerReference(HAPEventHandlerReference.parseHandlerInfo(jsonObj.optJSONObject(HAPEventProcess.HANDLERREFERENCE), parseService));
		
		return out;
	}
	
}



