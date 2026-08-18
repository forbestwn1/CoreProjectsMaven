package com.nosliw.core.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPGatewayOutput extends HAPSerializableImp{

	@HAPAttribute
	final public static String SCRIPTS = "scripts";

	@HAPAttribute
	final public static String DATA = "data";
	
	private List<HAPJSScriptInfo> m_scripts;
	
	private Object m_data;
	
	public HAPGatewayOutput() {
		this.m_scripts = new ArrayList<HAPJSScriptInfo>();
	}
	
	public HAPGatewayOutput(List<HAPJSScriptInfo> scripts, Object data){
		this();
		if(scripts!=null) {
			this.m_scripts.addAll(scripts);
		}
		this.m_data = data;
	}
	
	public List<HAPJSScriptInfo> getScripts(){  return this.m_scripts;  } 
	
	public Object getData(){  return this.m_data;  }
	public void setData(Object data) {   this.m_data = data;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(DATA, HAPManagerSerialize.getInstance().toStringValue(this.m_data, HAPSerializationFormat.JSON));
		jsonMap.put(SCRIPTS, HAPManagerSerialize.getInstance().toStringValue(this.m_scripts, HAPSerializationFormat.JSON));
	}

	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(DATA, HAPManagerSerialize.getInstance().toStringValue(this.m_data, HAPSerializationFormat.JAVASCRIPT));
		jsonMap.put(SCRIPTS, HAPManagerSerialize.getInstance().toStringValue(this.m_scripts, HAPSerializationFormat.JAVASCRIPT));
	}

	@Override
	protected boolean buildObjectByJson(Object json){  
		JSONObject jsonObj = (JSONObject)json;
		
		this.m_data = jsonObj.opt(DATA);
		
		JSONArray scriptJsonArray = jsonObj.optJSONArray(SCRIPTS);
		if(scriptJsonArray!=null) {
			for(int i=0; i<scriptJsonArray.length(); i++) {
				HAPJSScriptInfo jsScriptInfo = new HAPJSScriptInfo();
				jsScriptInfo.buildObject(scriptJsonArray.get(i), HAPSerializationFormat.JSON);
				this.m_scripts.add(jsScriptInfo);
			}
		}
		
		return true;
	}
}
