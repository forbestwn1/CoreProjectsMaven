package com.nosliw.core.data.expression.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;

@HAPEntityWithAttribute
public class HAPDefinitionRawDataExpression extends HAPSerializableImp{

	@HAPAttribute
	public static final String EXPRESSION = "expression";
	
	@HAPAttribute
	public static final String CONSTANT = "constant";
	
	private String m_expression;
	
	private Map<String, HAPData> m_constants = new LinkedHashMap<String, HAPData>();
	
	public String getExpression() {
		return this.m_expression;
	}
	
	public Map<String, HAPData> getConstants(){
		return this.m_constants;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(EXPRESSION, m_expression);
		jsonMap.put(CONSTANT, HAPManagerSerialize.getInstance().toStringValue(this.m_constants, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		this.m_expression = (String)jsonObj.opt(EXPRESSION);
		
		JSONObject constantJsonObj = jsonObj.optJSONObject(CONSTANT);
		for(Object key : constantJsonObj.keySet()) {
			String name = (String)key;
			HAPData constantData = HAPUtilityData.buildDataWrapperFromObject(constantJsonObj.get(name));
			this.m_constants.put(name, constantData);
		}
		
		return true;  
	}
	
}
