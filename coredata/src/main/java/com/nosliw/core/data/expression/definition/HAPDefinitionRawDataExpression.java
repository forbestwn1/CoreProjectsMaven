package com.nosliw.core.data.expression.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;

@HAPEntityWithAttribute
public class HAPDefinitionRawDataExpression extends HAPSerializableImp{

	@HAPAttribute
	public static final String EXPRESSION = "expression";
	
	@HAPAttribute
	public static final String CONSTANT = "constant";
	
	private HAPDefinitionDataExpression m_expression;
	
	private Map<String, HAPData> m_constants = new LinkedHashMap<String, HAPData>();
	
	public HAPDefinitionDataExpression getExpression() {		return this.m_expression;	}
	public void setExpression(HAPDefinitionDataExpression expression) {     this.m_expression = expression;      }
	
	public Map<String, HAPData> getConstants(){	return this.m_constants;	}
	public void addConstant(String name, HAPData data) {      this.m_constants.put(name, data);      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_expression!=null) {
			jsonMap.put(EXPRESSION, m_expression.toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(CONSTANT, HAPManagerSerialize.getInstance().toStringValue(this.m_constants, HAPSerializationFormat.JSON));
	}

	public static HAPDefinitionRawDataExpression buildRawDataExpression(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		HAPDefinitionRawDataExpression out = new HAPDefinitionRawDataExpression();
		
		JSONObject constantJsonObj = jsonObj.optJSONObject(CONSTANT);
		for(Object key : constantJsonObj.keySet()) {
			String name = (String)key;
			HAPData constantData = HAPUtilityData.buildDataWrapperFromObject(constantJsonObj.get(name));
			out.addConstant(name, constantData);
		}

		out.setExpression(HAPDefinitionDataExpression.buildDataExpressionDefinition(jsonObj.getJSONObject(EXPRESSION), parseService));
		
		return out;
	}
	
}
