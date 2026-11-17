package com.nosliw.core.application.spec.imp.basic;

import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.expression.dataexpression.standalone.HAPBlockDataExpressionStandAlone;

public class HAPBasicBlockDataExpressionStandAlone extends HAPBasicBrick implements HAPBlockDataExpressionStandAlone{

	public HAPBasicBlockDataExpressionStandAlone() {
		super(HAPEnumBrickType.DATAEXPRESSIONSTANDALONE_100);
		this.setAttributeValueWithValue(VALUE, new HAPDataExpressionStandAlone());;
	}
	
	@Override
	public HAPDataExpressionStandAlone getValue(){	return (HAPDataExpressionStandAlone)this.getAttributeValueOfValue(VALUE);	}
	
}
