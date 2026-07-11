package com.nosliw.core.data.expression.imp.basic;

import com.nosliw.core.data.expression.definition.HAPDefinitionDataExpression;

public class HAPBasicUtilityExpressionData {

	public static HAPBasicExpressionData buildBasicDataExpression(HAPDefinitionDataExpression dataExpressionDef) {
		return new HAPBasicExpressionData(HAPBasicUtilityOperand.buildBasicOperand(dataExpressionDef.getOperand()));
	}
	
}
