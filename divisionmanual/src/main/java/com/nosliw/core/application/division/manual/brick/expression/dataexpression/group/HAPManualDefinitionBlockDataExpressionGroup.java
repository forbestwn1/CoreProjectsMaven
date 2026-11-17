package com.nosliw.core.application.division.manual.brick.expression.dataexpression.group;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.spec.expression.dataexpression.group.HAPBlockDataExpressionGroup;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;

public class HAPManualDefinitionBlockDataExpressionGroup extends HAPManualDefinitionBrick{

	public HAPManualDefinitionBlockDataExpressionGroup() {
		super(HAPEnumBrickType.DATAEXPRESSIONGROUP_100);
		this.setAttributeValueWithValue(HAPBlockDataExpressionGroup.VALUE, new HAPManualDefinitionContainerDataExpression());
	}

	public HAPManualDefinitionContainerDataExpression getValue() {
		return (HAPManualDefinitionContainerDataExpression)this.getAttributeValueOfValue(HAPBlockDataExpressionGroup.VALUE);
	}
	
}
