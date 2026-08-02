package com.nosliw.core.application.common.command;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public abstract class HAPCommandHandlerReference extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSE_DOMAIN = "core.command.handler";
	
	@HAPAttribute
	public static final String TYPE = "type";
	
	private String m_handlerType;
  
	public String getHandlerType() {     return this.m_handlerType;     }
	
	public HAPCommandHandlerReference(String handlerType) {
		this.m_handlerType = handlerType;
	}
	
	public static HAPCommandHandlerReference parseHandler(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		return (HAPCommandHandlerReference)entityParseService.parseEntityJSONImplicitAttribute(jsonObj, TYPE, PARSE_DOMAIN);
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getHandlerType());
	}

}

abstract class HAPCommandHandlerReference_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {  return HAPCommandHandlerReference.PARSE_DOMAIN;   }

}
