package com.nosliw.core.data.expression.definition;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@Component
public class HAPDefinitionOperandVariable__HAPEntityParsable extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_VARIABLE;    }
	
	public static void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandVariable operandDefinition, HAPServiceParseEntity parseService) {
		operandDefinition.setVariableName(jsonObj.getString(HAPDefinitionOperandVariable.VARIABLENAME));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandVariable out = new HAPDefinitionOperandVariable();
		parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
