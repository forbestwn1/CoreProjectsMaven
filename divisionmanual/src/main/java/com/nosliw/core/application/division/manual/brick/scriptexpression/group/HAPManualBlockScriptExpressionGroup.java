package com.nosliw.core.application.division.manual.brick.scriptexpression.group;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.expression.scriptexpression.group.HAPBlockScriptExpressionGroup;
import com.nosliw.core.application.common.scriptexpressio.HAPContainerScriptExpression;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualBlockScriptExpressionGroup extends HAPManualBrickImp implements HAPBlockScriptExpressionGroup{

	public HAPManualBlockScriptExpressionGroup() {
		super(HAPEnumBrickType.SCRIPTEXPRESSIONGROUP_100);
	}

	@Override
	public void init() {
		this.setValue(new HAPContainerScriptExpression());
	}
	
	@Override
	public HAPContainerScriptExpression getValue() {  return (HAPContainerScriptExpression)this.getAttributeValueOfValue(VALUE);  }   
	public void setValue(HAPContainerScriptExpression value) {    this.setAttributeValueWithValue(VALUE, value);     } 
	
}
