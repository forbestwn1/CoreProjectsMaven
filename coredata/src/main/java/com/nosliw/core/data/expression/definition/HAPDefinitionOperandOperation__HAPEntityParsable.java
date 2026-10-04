package com.nosliw.core.data.expression.definition;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPDataTypeId;

@Component
public class HAPDefinitionOperandOperation__HAPEntityParsable extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_OPERATION;    }
	
	public static void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandOperation operandDefinition, HAPServiceParseEntity parseService) {
		operandDefinition.setBase(HAPDefinitionOperand.parseOperandDefinition(jsonObj.optJSONObject(HAPDefinitionOperandOperation.BASE), parseService));
		
		Object dataTypeObj = jsonObj.opt(HAPDefinitionOperandOperation.DATATYPEID);
		if(dataTypeObj!=null) {
			HAPDataTypeId dataTypeId = new HAPDataTypeId();
			if(dataTypeObj instanceof String) {
				dataTypeId.buildObject(dataTypeObj, HAPSerializationFormat.LITERATE);
			}
			else {
				dataTypeId.buildObject(dataTypeObj, HAPSerializationFormat.JSON);
			}
			operandDefinition.setDataTypeId(dataTypeId);
		}
		operandDefinition.setOperation(jsonObj.getString(HAPDefinitionOperandOperation.OPERATION));
		
		JSONObject parmsObj = jsonObj.getJSONObject(HAPDefinitionOperandOperation.PARMS);
		for(Object key : parmsObj.keySet()) {
			String name = (String)key;
			operandDefinition.addParm(name, HAPDefinitionOperand.parseOperandDefinition(parmsObj.getJSONObject(name), parseService));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandOperation out = new HAPDefinitionOperandOperation();
		parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
