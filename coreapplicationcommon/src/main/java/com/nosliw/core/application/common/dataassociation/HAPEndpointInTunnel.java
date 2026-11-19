package com.nosliw.core.application.common.dataassociation;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public abstract class HAPEndpointInTunnel extends HAPSerializableImp implements HAPEntityParsable{

	public static final String DOMAIN_PARSER = "dataassociaton.endpointintunnel";
	
	@HAPAttribute
	public static String TYPE = "type";

	private String m_endPointType;
	
	public HAPEndpointInTunnel(String endPointType) {
		this.m_endPointType = endPointType;
	}
	
	public String getEndPointType() {   return this.m_endPointType;    }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.m_endPointType);
	}
	
	public static HAPEndpointInTunnel parseTunnelEndpoint(Object obj, HAPServiceParseEntity parseService) {
		return (HAPEndpointInTunnel)parseService.parseEntityJSONImplicitAttribute((JSONObject)obj, TYPE, DOMAIN_PARSER);
	}
}

abstract class HAPEndpointInTunnel_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPEndpointInTunnel.DOMAIN_PARSER;   }
	
}
