package com.nosliw.core.application.division.manual.brick.expression.dataexpression.lib;

import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;
import com.nosliw.core.application.division.manual.core.HAPManualBrickWithEntityInfo;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.expression.dataexpression.library.HAPBlockDataExpressionElementInLibrary;

public class HAPManualBlockDataExpressionElementInLibrary extends HAPManualBrickWithEntityInfo implements HAPBlockDataExpressionElementInLibrary{

	public HAPManualBlockDataExpressionElementInLibrary() {
		super(HAPEnumBrickType.DATAEXPRESSIONLIBELEMENT_100);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(VALUE, new HAPDataExpressionStandAlone());;
	}
	
	@Override
	public HAPDataExpressionStandAlone getValue(){	return (HAPDataExpressionStandAlone)this.getAttributeValueOfValue(VALUE);	}
	
}
