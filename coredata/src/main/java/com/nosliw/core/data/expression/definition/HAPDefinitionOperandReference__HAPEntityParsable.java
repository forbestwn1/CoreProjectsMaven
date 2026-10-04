package com.nosliw.core.data.expression.definition;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@Component
class HAPDefinitionOperandReference__HAPEntityParsable extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_REFERENCE;    }
	
	public static void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandReference operandDefinition, HAPServiceParseEntity parseService) {
		operandDefinition.setReference(jsonObj.getString(HAPDefinitionOperandReference.REFERENCE));
		
		JSONObject varMppingsObj = jsonObj.getJSONObject(HAPDefinitionOperandReference.VARIABLEMAPPING);
		for(Object key : varMppingsObj.keySet()) {
			String name = (String)key;
			operandDefinition.addMapping(name, HAPDefinitionOperand.parseOperandDefinition(varMppingsObj.getJSONObject(name), parseService));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandReference out = new HAPDefinitionOperandReference();
		parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
