package com.nosliw.core.application.spec.common.task;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.spec.brick.container.HAPBrickContainer;

@HAPEntityWithAttribute
public interface HAPWithBrickTasks {

	@HAPAttribute
	public static String TASK = "task";

	HAPBrickContainer getTasks();

}
