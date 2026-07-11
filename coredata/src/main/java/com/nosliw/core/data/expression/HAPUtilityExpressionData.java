package com.nosliw.core.data.expression;

import com.nosliw.core.data.expression.definition.HAPParserDataExpression;
import com.nosliw.core.data.expression.imp.basic.HAPBasicExpressionData;
import com.nosliw.core.data.expression.imp.basic.HAPBasicUtilityExpressionData;

public class HAPUtilityExpressionData {

	public static HAPBasicExpressionData buildDataExpression(String expression, HAPParserDataExpression dataExpressionParser) {
		return HAPBasicUtilityExpressionData.buildBasicDataExpression(dataExpressionParser.parseExpression(expression));
	}

}
