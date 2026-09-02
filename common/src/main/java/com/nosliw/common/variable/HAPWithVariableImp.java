package com.nosliw.common.variable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;

public abstract class HAPWithVariableImp extends HAPSerializableImp implements HAPWithVariable{

	private Map<String, HAPVariableInfo> m_variablesInfo;

	public HAPWithVariableImp() {
		this.m_variablesInfo = new LinkedHashMap<String, HAPVariableInfo>();
	}
	
	@Override
	public Map<String, HAPVariableInfo> getVariablesInfo() {   return this.m_variablesInfo;   }
	@Override
	public void addVariableInfo(HAPVariableInfo variableInfo) {  this.m_variablesInfo.put(variableInfo.getVariableKey(), variableInfo);  }

	@Override
	public void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(HAPWithVariable.VARIABLEINFOS, HAPUtilityJson.buildJson(this.m_variablesInfo, HAPSerializationFormat.JSON));
		jsonMap.put(ENTITYTYPE, this.getWithVariableEntityType());
	}

	public static void buildToWithVariableImp(HAPWithVariableImp withVarImp, JSONObject jsonObj, HAPServiceParseEntity parseService) {
		JSONObject varInfosJsonObj = jsonObj.optJSONObject(VARIABLEINFOS);
		if(varInfosJsonObj!=null) {
			for(Object key : varInfosJsonObj.keySet()) {
				String name = (String)key;
			    withVarImp.addVariableInfo((HAPVariableInfo)parseService.parseEntityJSONExplicit(varInfosJsonObj.getJSONObject(name), HAPVariableInfo.ENTITYNAMEFORSERIALIZE));
			}
		}
	}
	
}
