package com.nosliw.core.application.division.manual.brick.interactive.interfacee.expression;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.interactive.interfacee.expression.HAPBlockInteractiveInterfaceExpression;
import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualBlockInteractiveInterfaceExpression extends HAPManualBrickImp implements HAPBlockInteractiveInterfaceExpression{

	public HAPManualBlockInteractiveInterfaceExpression() {
		super(HAPEnumBrickType.INTERACTIVEEXPRESSIONINTERFACE_100);
	}

	@Override
	public HAPInteractiveExpression getValue() {   return (HAPInteractiveExpression)this.getAttributeValueOfValue(HAPBlockInteractiveInterfaceExpression.VALUE);  }

	@Override
	public void setValue(HAPInteractiveExpression expressionInteractive) {      this.setAttributeValueWithValue(HAPManualBlockInteractiveInterfaceExpression.VALUE, expressionInteractive);       }

}
