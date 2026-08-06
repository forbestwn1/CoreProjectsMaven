package com.nosliw.core.data.expression.definition;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public class HAPDefinitionDataExpression extends HAPSerializableImp{

	public static String OPERAND = "operand";
	
	private HAPDefinitionOperand m_operand;
	
	public HAPDefinitionDataExpression() {	}
	
	public HAPDefinitionDataExpression(HAPDefinitionOperand operand) {
		this.m_operand = operand;
	}
	
	public HAPDefinitionOperand getOperand() {    return this.m_operand;      }
	public void setOperand(HAPDefinitionOperand operandDef) {     this.m_operand = operandDef;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(OPERAND, this.m_operand.toStringValue(HAPSerializationFormat.JSON));
	}

	public static HAPDefinitionDataExpression buildDataExpressionDefinition(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		HAPDefinitionDataExpression out = new HAPDefinitionDataExpression();
	    out.setOperand(HAPDefinitionOperand.parseOperandDefinition(jsonObj.getJSONObject(OPERAND), parseService));
	    return out;
	}


}
