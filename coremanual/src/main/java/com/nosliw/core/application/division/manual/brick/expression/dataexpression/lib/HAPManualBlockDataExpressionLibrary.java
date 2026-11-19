package com.nosliw.core.application.division.manual.brick.expression.dataexpression.lib;

import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.container.HAPBrickContainer;
import com.nosliw.core.application.spec.brick.expression.dataexpression.library.HAPBlockDataExpressionLibrary;

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
