package com.nosliw.core.application.spec.brick.expression.dataexpression.group;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.dataexpression.HAPContainerDataExpression;

@HAPEntityWithAttribute
public interface HAPBlockDataExpressionGroup extends HAPBrick{

	@HAPAttribute
	public static String VALUE = "value";
	
	HAPContainerDataExpression getValue();

}
