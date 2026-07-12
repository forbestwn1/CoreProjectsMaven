package com.nosliw.core.service.staticresource;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStaticRequestInfoLibrary extends HAPStaticRequestInfo{

	@HAPAttribute
	public static final String DOMAIN = "domain";

	@HAPAttribute
	public static final String NAME = "name";

	@HAPAttribute
	public static final String VERSION = "version";
	
	private String m_domain;

	private String m_name;

	private String m_version;

	public HAPStaticRequestInfoLibrary() {
		super(HAPStaticRequestInfo.STATIC_TYPE_LIBRARY);
	}
	
	public HAPStaticRequestInfoLibrary(String type, String domain, String name, String version) {
		this();
		this.m_domain = domain;
		this.m_name = name;
		this.m_version = version;
	}
	
	public String getDomain() {		return this.m_domain;	}
	public void setDomain(String domain) {    this.m_domain = domain;     }
	
	public String getName() {		return this.m_name;	}
	public void setName(String name) {   this.m_name = name;        }
	
	public String getVersion() {    return this.m_version;     }
	public void setVersion(String version) {      this.m_version = version;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DOMAIN, this.m_domain);
		jsonMap.put(NAME, this.m_name);
		jsonMap.put(VERSION, this.m_version);
	}
	
}

@Component
class HAPStaticRequestInfoLibrary__HAPEntityParsable extends HAPDataDefinition__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPStaticRequestInfo.STATIC_TYPE_LIBRARY;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStaticRequestInfoLibrary staticRequestInfoLib, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, staticRequestInfoLib, parseService);
		staticRequestInfoLib.setDomain((String)jsonObj.opt(HAPStaticRequestInfoLibrary.DOMAIN));
		staticRequestInfoLib.setName((String)jsonObj.opt(HAPStaticRequestInfoLibrary.NAME));
		staticRequestInfoLib.setVersion((String)jsonObj.opt(HAPStaticRequestInfoLibrary.VERSION));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStaticRequestInfoLibrary out = new HAPStaticRequestInfoLibrary();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
