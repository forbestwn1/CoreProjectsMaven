package com.nosliw.core.application.division.manual.brick.expression.dataexpression.group;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.expression.dataexpression.group.HAPBlockDataExpressionGroup;
import com.nosliw.core.application.common.dataexpression.HAPContainerDataExpression;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualBlockDataExpressionGroup extends HAPManualBrickImp implements HAPBlockDataExpressionGroup{

	public HAPManualBlockDataExpressionGroup() {
		super(HAPEnumBrickType.DATAEXPRESSIONGROUP_100);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(VALUE, new HAPContainerDataExpression());
	}
	
	@Override
	public HAPContainerDataExpression getValue(){	return (HAPContainerDataExpression)this.getAttributeValueOfValue(VALUE);	}


}
