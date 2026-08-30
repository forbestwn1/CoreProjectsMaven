package com.nosliw.core.application.common.dataexpression.definition;

import com.nosliw.common.container.HAPContainer;
import com.nosliw.core.data.expression.definition.HAPDefinitionDataExpression;

public class HAPDefinitionContainerDataExpression extends HAPContainer<HAPDefinitionItemInContainerDataExpression>{

	public String addDataExpression(HAPDefinitionDataExpression dataExpressionDef) {
		HAPDefinitionItemInContainerDataExpression item = new HAPDefinitionItemInContainerDataExpression(dataExpressionDef);
		return this.addItem(item);
	}
	
	
}
