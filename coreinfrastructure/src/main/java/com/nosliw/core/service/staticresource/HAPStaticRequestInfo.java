package com.nosliw.core.service.staticresource;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public class HAPStaticRequestInfo extends HAPSerializableImp implements HAPEntityParsable{

	public static final String DOMAIN_PARSE = "api.request.static";
	
	@HAPAttribute
	public static final String TYPE = "type";

	
	public HAPStaticRequestInfo(String type) {
		this.m_type = type;
	}
	
	private String m_type;

	public String getType() {		return this.m_type;	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(TYPE, this.getType());
	}

}

abstract class HAPStaticRequestInfo__HAPEntityParsable extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPStaticRequestInfo.DOMAIN_PARSE;   }

	protected void parseToEntity(JSONObject jsonObj, HAPStaticRequestInfo staticRequestInfo, HAPServiceParseEntity parseService) {
		
	}
	
}
