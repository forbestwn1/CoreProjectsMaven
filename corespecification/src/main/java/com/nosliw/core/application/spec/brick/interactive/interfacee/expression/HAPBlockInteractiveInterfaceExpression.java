package com.nosliw.core.application.spec.brick.interactive.interfacee.expression;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;

@HAPEntityWithAttribute
public interface HAPBlockInteractiveInterfaceExpression extends HAPBrick{

	@HAPAttribute
	public static String VALUE = "value";

	HAPInteractiveExpression getValue();

	void setValue(HAPInteractiveExpression value);
	
}
