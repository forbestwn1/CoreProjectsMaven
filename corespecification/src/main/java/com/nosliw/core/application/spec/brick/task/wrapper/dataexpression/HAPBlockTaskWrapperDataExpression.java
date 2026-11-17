package com.nosliw.core.application.spec.brick.task.wrapper.dataexpression;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;

@HAPEntityWithAttribute
public interface HAPBlockTaskWrapperDataExpression extends HAPBrick{

	@HAPAttribute
	public static String DATAEXPRESSION = "dataExpression";
	
	HAPDataExpressionStandAlone getDataExpression();
	
}
