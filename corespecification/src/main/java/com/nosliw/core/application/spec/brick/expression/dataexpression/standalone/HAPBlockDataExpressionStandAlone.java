package com.nosliw.core.application.spec.brick.expression.dataexpression.standalone;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;

@HAPEntityWithAttribute
public interface HAPBlockDataExpressionStandAlone extends HAPBrick{

	@HAPAttribute
	public static String VALUE = "value";
	
	HAPDataExpressionStandAlone getValue();
	
}
