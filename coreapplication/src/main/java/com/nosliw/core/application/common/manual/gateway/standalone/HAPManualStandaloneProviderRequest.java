package com.nosliw.core.application.common.manual.gateway.standalone;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPManualStandaloneProviderRequest extends HAPSerializableImp{

	@HAPAttribute
	public static final String IDPREFIX = "idPrefix";
	
	@HAPAttribute
	public static final String ID = "id";
	
	@HAPAttribute
	public static final String PARMS = "parms";
	
	private String m_idPrefix;
	
	private String m_id;
	
	private Object m_parms;

	public String getIdPrefix() {     return this.m_idPrefix;      }

	public String getId() {    return this.m_id;    }
	
	public Object getParms() {     return this.m_parms;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(IDPREFIX, this.m_idPrefix);
		jsonMap.put(ID, this.m_id);
		jsonMap.put(PARMS, HAPManagerSerialize.getInstance().toStringValue(this.m_parms, HAPSerializationFormat.JSON));
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_idPrefix = (String)jsonObj.opt(IDPREFIX);
		this.m_id = (String)jsonObj.opt(ID);
		this.m_parms = jsonObj.optJSONObject(PARMS);
		
		return true;  
	}
	
}
