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
import com.nosliw.core.application.HAPIdBrickInBundle;

@HAPEntityWithAttribute
public class HAPEventEmitter extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static final String EMITTERID = "emitterId";

	@HAPAttribute
	public static final String CHILDID = "childId";

	@HAPAttribute
	public static final String EVENTDEFINITION = "eventDefinition";

	private HAPIdBrickInBundle m_emitterBrickId;

	private String m_childId;
	
	private HAPEventDefinition m_eventDefinition;
	
	public HAPEventEmitter() {	}

	public HAPEventEmitter(HAPIdBrickInBundle emitterBrickId, String childId, HAPEventDefinition eventDefinition) {
		this.m_emitterBrickId = emitterBrickId;
		this.m_childId = childId;
		this.m_eventDefinition = eventDefinition;
	}

	public HAPIdBrickInBundle getEmitterBrickId() {     return this.m_emitterBrickId;       }
	public void setEmitterBrickId(HAPIdBrickInBundle emitterBrickId) {    this.m_emitterBrickId = emitterBrickId;        }
	
	public String getChildId() {    return this.m_childId;      }
	public void setChildId(String childId) {     this.m_childId = childId;        }
	
    public HAPEventDefinition getEventDefinition() {      return this.m_eventDefinition;      }
    public void setEventDefinition(HAPEventDefinition eventDef) {     this.m_eventDefinition = eventDef;       }

    public static HAPEventEmitter parseEventEmitter(Object obj, HAPServiceParseEntity entityParseService) {
    	return (HAPEventEmitter)entityParseService.parseEntityJSONExplicit((JSONObject)obj, HAPEventEmitter.class.getName());
    }
    
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

@Component
class HAPEventEmitter_Parser implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPEventEmitter.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPEventEmitter out = new HAPEventEmitter();
		
		JSONObject jsonObj = (JSONObject)obj;
		out.setChildId((String)jsonObj.opt(HAPEventEmitter.CHILDID));
		
		JSONObject emitterIdJsonobj = jsonObj.getJSONObject(HAPEventEmitter.EMITTERID);
		HAPIdBrickInBundle emmiterBrickId = new HAPIdBrickInBundle();
		emmiterBrickId.buildObject(emitterIdJsonobj, HAPSerializationFormat.JSON);
		out.setEmitterBrickId(emmiterBrickId);
		
		out.setEventDefinition(HAPEventDefinition.parseEventDefinition(jsonObj.optJSONObject(HAPEventEmitter.EVENTDEFINITION), parseService));
		
		return out;
	}
	
}
