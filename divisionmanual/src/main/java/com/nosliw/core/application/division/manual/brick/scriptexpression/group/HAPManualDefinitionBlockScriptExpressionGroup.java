package com.nosliw.core.application.division.manual.brick.scriptexpression.group;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.spec.expression.dataexpression.group.HAPBlockDataExpressionGroup;
import com.nosliw.core.application.common.scriptexpressio.definition.HAPDefinitionContainerScriptExpression;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;

public class HAPManualDefinitionBlockScriptExpressionGroup extends HAPManualDefinitionBrick{

	public static final String VALUE = "value";
	
	public HAPManualDefinitionBlockScriptExpressionGroup() {
		super(HAPEnumBrickType.SCRIPTEXPRESSIONGROUP_100);
		this.setAttributeValueWithValue(VALUE, new HAPDefinitionContainerScriptExpression());
	}
	
	public HAPDefinitionContainerScriptExpression getValue() {
		return (HAPDefinitionContainerScriptExpression)this.getAttributeValueOfValue(HAPBlockDataExpressionGroup.VALUE);
	}
}
