package com.nosliw.core.application.spec.brick.expression.scriptexpression.group;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.scriptexpressio.HAPContainerScriptExpression;

@HAPEntityWithAttribute
public interface HAPBlockScriptExpressionGroup extends HAPBrick{

	@HAPAttribute
	public static String VALUE = "value";
	
	HAPContainerScriptExpression getValue();
}
