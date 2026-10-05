package com.nosliw.core.application.entity.app.expressionbuild;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandConstant;

@HAPEntityWithAttribute
public class HAPDataBuildOperandConstant extends HAPDefinitionOperandConstant implements HAPDataBuildOperand{

	@HAPAttribute
	public static final String DATADEFINITION = "dataDefinition";
	
	private HAPDataDefinition m_dataDefinition;
	
}
