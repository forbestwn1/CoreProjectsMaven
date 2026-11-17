package com.nosliw.core.application.spec.brick.test.complex.task;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.valueport.HAPIdElement;
import com.nosliw.core.application.spec.brick.interactive.interfacee.expression.HAPBlockInteractiveInterfaceExpression;
import com.nosliw.core.application.spec.brick.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;

@HAPEntityWithAttribute
public interface HAPBlockTestComplexTask extends HAPBrick{

	@HAPAttribute
	public static String PARM = "parm";

	@HAPAttribute
	public static String VARIABLE = "variable";

	@HAPAttribute
	public static String INTERACTIVETASK = "interactiveTask";

	@HAPAttribute
	public static String INTERACTIVETASKRESULT = "interactiveTaskResult";

	@HAPAttribute
	public static String INTERACTIVEEXPRESSION = "interactiveExpression";

	Map<String, Object> getParms();

	Map<String, HAPIdElement> getVariables();

	HAPBlockInteractiveInterfaceTask getTaskInteractive();

	String getTaskInteractiveResult();

	HAPBlockInteractiveInterfaceExpression getExpressionInteractive();

}
