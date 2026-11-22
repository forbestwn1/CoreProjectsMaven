package com.nosliw.core.application.common.interactive;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.interfac.HAPEntityOrReference;

@HAPEntityWithAttribute
public interface HAPWithBlockInteractiveExpression {

	@HAPAttribute
	public static String EXPRESSIONINTERFACE = "expressionInterface";
	
	public HAPEntityOrReference getExpressionInterface();

	public void setExpressionInterface(HAPEntityOrReference expressionInterfac);
}
