package com.nosliw.core.gateway;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPServiceInfo extends HAPSerializableImp{

	@HAPAttribute
	public static final String SERVICE_COMMAND = "command";
	@HAPAttribute
	public static final String SERVICE_PARMS = "parms";
	
	private String m_command;
	private JSONObject m_parms;
	
	public HAPServiceInfo(String command, JSONObject parmJson){
		this.m_command = command;
		this.m_parms = parmJson;
	}

	public HAPServiceInfo(JSONObject serviceJson) throws JSONException {
		m_command = serviceJson.getString(SERVICE_COMMAND);
		this.m_parms = serviceJson.optJSONObject(SERVICE_PARMS);
	}
	
	public String getCommand(){	return this.m_command;	}
	public JSONObject getParms(){ return this.m_parms; }
	public Object getParm(String name){ return this.m_parms.opt(name);  }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(SERVICE_COMMAND, this.m_command);
		if(this.m_parms!=null) {
			jsonMap.put(SERVICE_PARMS, this.m_parms.toString());
		}
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_command = jsonObj.getString(SERVICE_COMMAND);
		this.m_parms = jsonObj.optJSONObject(SERVICE_PARMS);
		return true;  
	}

}
