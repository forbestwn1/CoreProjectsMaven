package com.nosliw.core.application.spec.imp.basic;

import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.interactive.interfacee.expression.HAPBlockInteractiveInterfaceExpression;

public class HAPBasicBlockInteractiveInterfaceExpression extends HAPBasicBrick implements HAPBlockInteractiveInterfaceExpression{

	public HAPBasicBlockInteractiveInterfaceExpression(){
		super(HAPEnumBrickType.INTERACTIVEEXPRESSIONINTERFACE_100);
	}
	
	@Override
	public HAPInteractiveExpression getValue() {    return (HAPInteractiveExpression)this.getAttributeValueOfValue(VALUE);  }
	@Override
	public void setValue(HAPInteractiveExpression value) {   this.setAttributeValueWithValue(VALUE, value);      }

}