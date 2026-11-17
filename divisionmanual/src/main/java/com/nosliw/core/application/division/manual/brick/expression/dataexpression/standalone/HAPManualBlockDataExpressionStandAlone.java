package com.nosliw.core.application.division.manual.brick.expression.dataexpression.standalone;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.spec.expression.dataexpression.standalone.HAPBlockDataExpressionStandAlone;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualBlockDataExpressionStandAlone extends HAPManualBrickImp implements HAPBlockDataExpressionStandAlone{

	public HAPManualBlockDataExpressionStandAlone() {
		super(HAPEnumBrickType.DATAEXPRESSIONSTANDALONE_100);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(VALUE, new HAPDataExpressionStandAlone());;
	}
	
	@Override
	public HAPDataExpressionStandAlone getValue(){	return (HAPDataExpressionStandAlone)this.getAttributeValueOfValue(VALUE);	}
	
}
