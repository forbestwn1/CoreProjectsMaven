package com.nosliw.core.application.common.command;

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
public class HAPCommandProcess extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public final static String DEFINITION = "definition"; 

	@HAPAttribute
	public final static String HANDLER = "handler"; 

	private HAPCommandDefinition m_commandDefinition;

	private HAPCommandHandlerReference m_commandHandlerReference;
	
	public void setCommandDefinition(HAPCommandDefinition commandDefinition) {     this.m_commandDefinition = commandDefinition;      }
	public HAPCommandDefinition getCommandDefinition() {       return this.m_commandDefinition;        }
	
	public void setCommandHandler(HAPCommandHandlerReference commandHandlerReference) {       this.m_commandHandlerReference = commandHandlerReference;          }
	public HAPCommandHandlerReference getCommandHandler() {      return this.m_commandHandlerReference;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_commandDefinition!=null) {
			jsonMap.put(DEFINITION, this.m_commandDefinition.toStringValue(HAPSerializationFormat.JSON));
		}
		if(this.m_commandHandlerReference!=null) {
			jsonMap.put(HANDLER, this.m_commandHandlerReference.toStringValue(HAPSerializationFormat.JSON));
		}
	}
	
	public static HAPCommandProcess parseCommandProcess(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		return (HAPCommandProcess)entityParseService.parseEntityJSONExplicit(jsonObj, HAPCommandProcess.class.getName());
	}
}

@Component
class HAPCommandProcess_Parser implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPCommandProcess.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPCommandProcess out = new HAPCommandProcess();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject definitionJsonobj = jsonObj.getJSONObject(HAPCommandProcess.DEFINITION);
		if(definitionJsonobj!=null) {
			out.setCommandDefinition(HAPCommandDefinition.parseCommandDefinition(definitionJsonobj, parseService));
		}
		
		JSONObject handlerJsonobj = jsonObj.getJSONObject(HAPCommandProcess.HANDLER);
		if(handlerJsonobj!=null) {
			out.setCommandHandler(HAPCommandHandlerReference.parseHandler(handlerJsonobj, parseService));
		}
		
		return out;
	}
	
}

