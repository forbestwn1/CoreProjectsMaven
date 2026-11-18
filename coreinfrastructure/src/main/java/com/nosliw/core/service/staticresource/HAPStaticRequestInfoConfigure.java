package com.nosliw.core.service.staticresource;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPStaticRequestInfoConfigure extends HAPStaticRequestInfo{

	@HAPAttribute
	public static final String NAME = "name";

	private String m_name;
	
	public HAPStaticRequestInfoConfigure() {
		super(HAPConstantShared.STATIC_REQUEST_TYPE_CONFIGURE);
	}

	public String getName() {      return this.m_name;    }
	public void setName(String name) {     this.m_name = name;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(NAME, this.m_name);
	}
}

@Component
class HAPStaticRequestInfoConfigure__HAPEntityParsable extends HAPStaticRequestInfo__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.STATIC_REQUEST_TYPE_CONFIGURE;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStaticRequestInfoConfigure staticRequestInfoConfigure, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, staticRequestInfoConfigure, parseService);
		staticRequestInfoConfigure.setName(jsonObj.getString(HAPStaticRequestInfoConfigure.NAME));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStaticRequestInfoConfigure out = new HAPStaticRequestInfoConfigure();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
