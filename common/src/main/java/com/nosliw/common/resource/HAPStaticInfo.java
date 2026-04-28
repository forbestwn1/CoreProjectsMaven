package com.nosliw.common.resource;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPStaticInfo extends HAPSerializableImp{

	@HAPAttribute
	public static final String TYPE = "type";

	@HAPAttribute
	public static final String DOMAIN = "domain";

	@HAPAttribute
	public static final String NAME = "name";

	@HAPAttribute
	public static final String VERSION = "version";
	
	public static final String STATIC_TYPE_LIBRARY = "library";
	public static final String STATIC_TYPE_FILE = "file";
	
	private String m_type;
	
	private String m_domain;

	private String m_name;

	private String m_version;

	public HAPStaticInfo() {}
	
	public HAPStaticInfo(String type, String domain, String name, String version) {
		this.m_type = type;
		this.m_domain = domain;
		this.m_name = name;
		this.m_version = version;
	}
	
	public String getType() {		return this.m_type;	}
	
	public String getDomain() {		return this.m_domain;	}
	
	public String getName() {		return this.m_name;	}
	
	public String getVersion() {    return this.m_version;     }

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		this.m_type = (String)jsonObj.opt(TYPE);
		this.m_domain = (String)jsonObj.opt(DOMAIN);
		this.m_name = (String)jsonObj.opt(NAME);
		this.m_version = (String)jsonObj.opt(VERSION);
		
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(TYPE, this.m_type);
		jsonMap.put(DOMAIN, this.m_domain);
		jsonMap.put(NAME, this.m_name);
		jsonMap.put(VERSION, this.m_version);
	}
	
}
