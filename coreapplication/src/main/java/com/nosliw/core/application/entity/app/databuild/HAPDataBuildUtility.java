package com.nosliw.core.application.entity.app.databuild;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinitionWritableWithInit;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.expression.definition.HAPDefinitionDataExpression;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperand;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandConstant;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandOperation;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandVariable;
import com.nosliw.core.data.expression.definition.HAPDefinitionParmInOperationOperand;

public class HAPDataBuildUtility {

	public static HAPDataBuild buildDataBuildFromDataDefinition(HAPDataDefinition dataDefinition) {
    	HAPDataBuild out = null;
		if(dataDefinition.getType().equals(HAPConstantShared.DATADEFINITION_TYPE_WRITEABLEWITHINIT)){
			out = ((HAPDataDefinitionWritableWithInit)dataDefinition).getInitDataBuild();
		}
		return out;
	}

	public static HAPDataBuildOperand getOperand(HAPDataBuild dataBuild) {
		return dataBuild.getExpressions().get(dataBuild.getExpressionChosen()).getOperand();
	}
	
	public static HAPData getData(HAPDataBuild dataBuild) {
		HAPData out = null;
		if(dataBuild!=null && HAPConstantShared.DATABUILD_CHOSEN_CONSTANT.equals(dataBuild.getExpressionChosen())) {
			HAPDataBuildExpression expression = dataBuild.getExpressions().get(HAPConstantShared.DATABUILD_CHOSEN_CONSTANT);
			if(expression!=null) {
				HAPDataBuildOperandConstant constantOperand = (HAPDataBuildOperandConstant)expression.getOperand();
				if(constantOperand!=null) {
					out = constantOperand.getData();
				}
			}
		}
		return out;
	}

	public static HAPDefinitionDataExpression getExpressionObj(HAPDataBuild dataBuild) {
		HAPDefinitionDataExpression out = null;
		if(dataBuild!=null && HAPConstantShared.DATABUILD_CHOSEN_EXPRESSION.equals(dataBuild.getExpressionChosen())) {
			HAPDataBuildExpression expressionDataBuild = dataBuild.getExpressions().get(HAPConstantShared.DATABUILD_CHOSEN_EXPRESSION);
			if(expressionDataBuild!=null) {
				out = new HAPDefinitionDataExpression();
				out.setOperand(dataBuildOperandToDefinition(expressionDataBuild.getOperand()));
			}
		}
		return out;
	}
	
	private static HAPDefinitionOperand dataBuildOperandToDefinition(HAPDataBuildOperand dataBuildOperand) {
		if(dataBuildOperand==null) {
			return null;
		}
		
		HAPDefinitionOperand out = null;
	    String type = dataBuildOperand.getType();
		switch(type) {
		case HAPConstantShared.EXPRESSION_OPERAND_CONSTANT:
			HAPDataBuildOperandConstant constantOperandDataBuild = (HAPDataBuildOperandConstant)dataBuildOperand;
			HAPDefinitionOperandConstant constantOperandDef = new HAPDefinitionOperandConstant();
			constantOperandDef.setData(constantOperandDataBuild.getData());
			out = constantOperandDef;
			break;
		case HAPConstantShared.EXPRESSION_OPERAND_VARIABLE:
			HAPDataBuildOperandVariable variableOperandDataBuild = (HAPDataBuildOperandVariable)dataBuildOperand;
			HAPDefinitionOperandVariable variableOperandDef = new HAPDefinitionOperandVariable();
			variableOperandDef.setVariableName(variableOperandDataBuild.getVariableName());
			out = variableOperandDef;
			break;
		case HAPConstantShared.EXPRESSION_OPERAND_OPERATION:
			HAPDataBuildOperandOperation operationOperandDataBuild = (HAPDataBuildOperandOperation)dataBuildOperand;
			HAPDefinitionOperandOperation operationOperandDef = new HAPDefinitionOperandOperation();
			
			operationOperandDef.setBase(dataBuildOperandToDefinition(operationOperandDataBuild.getBase()));
			operationOperandDef.setDataTypeId(operationOperandDataBuild.getDataTypeId());
			operationOperandDef.setOperation(operationOperandDataBuild.getOperaion());
			
			for(HAPDataBuildParmInOperationOperand parmDataBuild : operationOperandDataBuild.getParms()) {
				HAPDefinitionOperand parmOperandDef = dataBuildOperandToDefinition(getOperand(parmDataBuild.getValue()));
				
				operationOperandDef.addParm(parmDataBuild.getName(), parmOperandDef);
				
				HAPDefinitionParmInOperationOperand parmDef = new HAPDefinitionParmInOperationOperand();
				parmDef.setDataTypeCriteria(parmDataBuild.getDataTypeCriteria());
				parmDef.setName(parmDataBuild.getName());
				parmDef.setOperand(operationOperandDef);
				operationOperandDef.addParm1(parmDef);
			}
			
			break;
		}
		return out;
	}
	
	public static HAPDataBuild buildDataBuildByConstant(HAPData data) {
		HAPDataBuild out = new HAPDataBuild();
		out.setExpressionChosen(HAPConstantShared.DATABUILD_CHOSEN_CONSTANT);
		
		HAPDataBuildOperandConstant constantOperand = new HAPDataBuildOperandConstant();
		constantOperand.setData(data);
		
		HAPDataBuildExpression expression = new HAPDataBuildExpression();
		expression.setOperand(constantOperand);
		
		out.addExpression(HAPConstantShared.DATABUILD_CHOSEN_CONSTANT, expression);
		
		return out;
	}
	
}
