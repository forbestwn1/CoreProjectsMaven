package com.nosliw.core.application.brick.imp.basic;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.interactive.interfacee.expression.HAPBlockInteractiveInterfaceExpression;
import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;

public class HAPBasicBlockInteractiveInterfaceExpression extends HAPBasicBrick implements HAPBlockInteractiveInterfaceExpression{

	public HAPBasicBlockInteractiveInterfaceExpression(){
		super(HAPEnumBrickType.INTERACTIVEEXPRESSIONINTERFACE_100);
	}
	
	@Override
	public HAPInteractiveExpression getValue() {    return (HAPInteractiveExpression)this.getAttributeValueOfValue(VALUE);  }
	@Override
	public void setValue(HAPInteractiveExpression value) {   this.setAttributeValueWithValue(VALUE, value);      }

}