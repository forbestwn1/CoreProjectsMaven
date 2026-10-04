package com.nosliw.core.data.expression.definition;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPUtilityData;

@Component
public class HAPDefinitionOperandConstant__HAPEntityParsable  extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_CONSTANT;    }
	
	public static void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandConstant operandDefinition, HAPServiceParseEntity parseService) {
		operandDefinition.setStringValue((String)jsonObj.opt(HAPDefinitionOperandConstant.CONSTANTSTR));
		operandDefinition.setData(HAPUtilityData.buildDataWrapperFromObject(jsonObj.opt(HAPDefinitionOperandConstant.DATA)));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandConstant out = new HAPDefinitionOperandConstant();
		parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
