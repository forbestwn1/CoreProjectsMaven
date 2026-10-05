package com.nosliw.core.data.expression.definition;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;

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

		JSONArray parms1JsonArray = jsonObj.optJSONArray(HAPDefinitionOperandOperation.PARMS1);
		for(int i=0; i<parms1JsonArray.length(); i++) {
			operandDefinition.addParm1(parseParmInOperationOperand(parms1JsonArray.getJSONObject(i), parseService));
		}

	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandOperation out = new HAPDefinitionOperandOperation();
		parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

	private static HAPDefinitionParmInOperationOperand parseParmInOperationOperand(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		HAPDefinitionParmInOperationOperand out = new HAPDefinitionParmInOperationOperand();
		
		out.setName((String)jsonObj.opt(HAPDefinitionParmInOperationOperand.NAME));
		
		String dataCriteriaStr = (String)jsonObj.opt(HAPDefinitionParmInOperationOperand.CRITERIA);
		if(dataCriteriaStr!=null) {
			out.setDataTypeCriteria(HAPUtilityCriteria.parseCriteria(HAPDefinitionParmInOperationOperand.CRITERIA));
		}
		
		out.setOperand(HAPDefinitionOperand.parseOperandDefinition(jsonObj.optJSONObject(HAPDefinitionParmInOperationOperand.OPERAND), parseService));
		
		return out;
	}

}
