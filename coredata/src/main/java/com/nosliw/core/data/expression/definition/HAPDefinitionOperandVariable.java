package com.nosliw.core.data.expression.definition;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPDefinitionOperandVariable extends HAPDefinitionOperand{

	public static String VARIABLENAME = "variableName";
	
	protected String m_variableName;

	public HAPDefinitionOperandVariable(){
		super(HAPConstantShared.EXPRESSION_OPERAND_VARIABLE);
	}

	public HAPDefinitionOperandVariable(String name){
		this();
		this.m_variableName = name;
	}

	public String getVariableName(){  return this.m_variableName;  }
	public void setVariableName(String name){   this.m_variableName = name;  }


	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VARIABLENAME, this.m_variableName);
	}
}

@Component
class HAPDefinitionOperandVariable__HAPEntityParsable extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_VARIABLE;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandVariable operandDefinition, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, operandDefinition, parseService);

		operandDefinition.setVariableName(jsonObj.getString(HAPDefinitionOperandVariable.VARIABLENAME));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandVariable out = new HAPDefinitionOperandVariable();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
