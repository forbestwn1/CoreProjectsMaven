package com.nosliw.core.application.common.dataexpression.definition;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;

@HAPEntityWithAttribute
public interface HAPDefinitionWithDataExpression {

	@HAPAttribute
	public static String DATAEXPRESSION = "dataExpression";

	HAPDefinitionContainerDataExpression getDataExpressions();

}
