package com.nosliw.core.application.brick.task.wrapper.dataexpression;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;

public class HAPBlockTaskWrapperDataExpressionImp extends HAPBrickImp implements HAPBlockTaskWrapperDataExpression{

	public HAPBlockTaskWrapperDataExpressionImp(String division) {
		super(HAPEnumBrickType.TASKWRAPPERDATAEXPRESSION, division);
		this.setAttributeValueWithValue(DATAEXPRESSION, new HAPDataExpressionStandAlone());;
	}
	
	@Override
	public HAPDataExpressionStandAlone getDataExpression(){	return (HAPDataExpressionStandAlone)this.getAttributeValueOfValue(DATAEXPRESSION);	}
	
}
