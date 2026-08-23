package com.nosliw.core.service.staticresource;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStaticResponseInfo extends HAPSerializableImp implements HAPEntityParsable{

	public static final String DOMAIN_PARSE = "api.response.static";

	@HAPAttribute
	public static final String TYPE = "type";

	private String m_type;
	
	public HAPStaticResponseInfo(String type) {
		this.m_type = type;
	}
	
	public String getType() {     return this.m_type;    }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.m_type);
	}

	
}

abstract class HAPStaticResponseInfo__HAPEntityParsable extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPStaticResponseInfo.DOMAIN_PARSE;   }

	protected void parseToEntity(JSONObject jsonObj, HAPStaticResponseInfo staticRequestInfo, HAPServiceParseEntity parseService) {
		
	}
	
}
