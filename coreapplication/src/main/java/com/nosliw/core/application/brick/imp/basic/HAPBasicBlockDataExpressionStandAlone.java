package com.nosliw.core.application.brick.imp.basic;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.expression.dataexpression.standalone.HAPBlockDataExpressionStandAlone;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;

public class HAPBasicBlockDataExpressionStandAlone extends HAPBasicBrick implements HAPBlockDataExpressionStandAlone{

	public HAPBasicBlockDataExpressionStandAlone() {
		super(HAPEnumBrickType.DATAEXPRESSIONSTANDALONE_100);
		this.setAttributeValueWithValue(VALUE, new HAPDataExpressionStandAlone());;
	}
	
	@Override
	public HAPDataExpressionStandAlone getValue(){	return (HAPDataExpressionStandAlone)this.getAttributeValueOfValue(VALUE);	}
	
}
