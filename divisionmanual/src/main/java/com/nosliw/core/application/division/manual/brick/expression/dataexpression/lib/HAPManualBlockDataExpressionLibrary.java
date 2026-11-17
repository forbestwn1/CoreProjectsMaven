package com.nosliw.core.application.division.manual.brick.expression.dataexpression.lib;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.spec.container.HAPBrickContainer;
import com.nosliw.core.application.common.brick.spec.expression.dataexpression.library.HAPBlockDataExpressionLibrary;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualBlockDataExpressionLibrary extends HAPManualBrickImp implements HAPBlockDataExpressionLibrary{

	public HAPManualBlockDataExpressionLibrary() {
		super(HAPEnumBrickType.DATAEXPRESSIONLIBELEMENT_100);
	}

	@Override
	public void init() {	
		super.init();
	}

	@Override
	public HAPBrickContainer getItems() {
		return (HAPBrickContainer)this.getAttributeValueOfBrickLocal(ITEM);
	}
}
