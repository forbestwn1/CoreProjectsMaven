package com.nosliw.core.data.expression.imp.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPDataWrapper;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperand;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandAttribute;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandConstant;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandOperation;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandReference;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandVariable;
import com.nosliw.core.data.expression.definition.HAPDefinitionParmInOperationOperand;

public class HAPBasicUtilityOperand {

	static public void traverseAllOperand(HAPBasicWrapperOperand operandWrapper, HAPBasicHandlerOperand task, Object data){
		if(task.processOperand(operandWrapper, data)){
			List<HAPBasicWrapperOperand> children = operandWrapper.getOperand().getChildren();
			for(HAPBasicWrapperOperand child : children){
				traverseAllOperand(child, task, data);
			}
			task.postPross(operandWrapper, data);
		}
	}
	
	public static HAPBasicOperand buildBasicOperand(HAPDefinitionOperand operandDef) {
		HAPBasicOperand out = null;
		
		String operandType = operandDef.getType();
		
		if(operandType.equals(HAPConstantShared.EXPRESSION_OPERAND_ATTRIBUTEOPERATION)) {
			out = buildBasicOperandAttribute((HAPDefinitionOperandAttribute)operandDef);
		}
		else if(operandType.equals(HAPConstantShared.EXPRESSION_OPERAND_CONSTANT)) {
			out = buildBasicOperandConstant((HAPDefinitionOperandConstant)operandDef);
		}
		else if(operandType.equals(HAPConstantShared.EXPRESSION_OPERAND_VARIABLE)) {
			out = buildBasicOperandVariable((HAPDefinitionOperandVariable)operandDef);
		}
		else if(operandType.equals(HAPConstantShared.EXPRESSION_OPERAND_OPERATION)) {
			out = buildOperandOperation((HAPDefinitionOperandOperation)operandDef);
		}
		else if(operandType.equals(HAPConstantShared.EXPRESSION_OPERAND_REFERENCE)) {
			out = buildOperandReference((HAPDefinitionOperandReference)operandDef);
		}
		
		return out;
	}
	
	private static HAPBasicOperandAttribute buildBasicOperandAttributeXXX(HAPDefinitionOperandAttribute operandDef) {
		HAPBasicOperandAttribute out = new HAPBasicOperandAttribute(operandDef);
		out.setBase(buildBasicOperand(operandDef.getBase()));
		return out;
	}

	private static HAPBasicOperand buildBasicOperandAttribute(HAPDefinitionOperandAttribute operandDef) {
		//turn attribute operand to operation operand
		List<HAPDefinitionParmInOperationOperand> parms = new ArrayList<HAPDefinitionParmInOperationOperand>();
		parms.add(new HAPDefinitionParmInOperationOperand(HAPConstantShared.DATAOPERATION_COMPLEX_GETCHILDDATA_NAME, new HAPDefinitionOperandConstant(new HAPDataWrapper(new HAPDataTypeId("test.string;1.0.0"), operandDef.getAttribute()))));
		HAPDefinitionOperandOperation operationOprand = new HAPDefinitionOperandOperation(operandDef.getBase(), HAPConstantShared.DATAOPERATION_COMPLEX_GETCHILDDATA, parms);
		
		return buildBasicOperand(operationOprand);
		
//		HAPBasicOperandOperation out = new HAPBasicOperandOperation(operationOprand);
//		out.setBase(buildBasicOperand(operandDef.getBase()));
//		return out;
	}

	private static HAPBasicOperandConstant buildBasicOperandConstant(HAPDefinitionOperandConstant operandDef) {
		HAPBasicOperandConstant out = new HAPBasicOperandConstant(operandDef);
		return out;
	}
	
	private static HAPBasicOperandVariable buildBasicOperandVariableXXX(HAPDefinitionOperandVariable operandDef) {
		HAPBasicOperandVariable out = new HAPBasicOperandVariable(operandDef);
		return out;
	}

	private static HAPBasicOperand buildBasicOperandVariable(HAPDefinitionOperandVariable operandDef) {
		String varName = operandDef.getVariableName();
		String[] segs = varName.split("\\.");
		
		if(segs.length==1) {
			//single variable
			return new HAPBasicOperandVariable(operandDef);
		}
		else {
			//variable with attributes
			HAPDefinitionOperand operandDefOut = null;
			for(int i=0; i<segs.length; i++) {
				if(i==0) {
					operandDefOut = new HAPDefinitionOperandVariable(segs[0]);
				}
				else {
					operandDefOut = new HAPDefinitionOperandAttribute(operandDefOut, segs[i]);
				}
			}
			return buildBasicOperand(operandDefOut);
		}
	}

	private static HAPBasicOperandOperation buildOperandOperation(HAPDefinitionOperandOperation operandDef) {
		HAPBasicOperandOperation out = new HAPBasicOperandOperation(operandDef);
		if(operandDef.getBase()!=null) {
			out.setBase(buildBasicOperand(operandDef.getBase()));
		}
		
		Map<String, HAPDefinitionOperand> parmsDef = operandDef.getParms();
		for(String parmName : parmsDef.keySet()) {
			out.setParm(parmName, buildBasicOperand(parmsDef.get(parmName)));
		}
		return out;
	}
	
	private static HAPBasicOperandReference buildOperandReference(HAPDefinitionOperandReference operandDef) {
		HAPBasicOperandReference out = new HAPBasicOperandReference(operandDef);
		
		Map<String, HAPDefinitionOperand> refMapping = operandDef.getMapping();
		for(String name : refMapping.keySet()) {
			out.addMapping(name, buildBasicOperand(refMapping.get(name)));
		}
		
		return out;
	}

}
