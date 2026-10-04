package com.nosliw.core.data.expression.definition;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@Component
class HAPDefinitionOperandAttribute__HAPEntityParsable extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_ATTRIBUTEOPERATION;    }
	
	public static void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandAttribute operandDefinition, HAPServiceParseEntity parseService) {
		operandDefinition.setAttribute(jsonObj.getString(HAPDefinitionOperandAttribute.ATTRIBUTE));
		operandDefinition.setBase(HAPDefinitionOperand.parseOperandDefinition(jsonObj.optJSONObject(HAPDefinitionOperandAttribute.BASE), parseService));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandAttribute out = new HAPDefinitionOperandAttribute();
		parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
