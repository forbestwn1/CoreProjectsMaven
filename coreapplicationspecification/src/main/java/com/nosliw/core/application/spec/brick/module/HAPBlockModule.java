package com.nosliw.core.application.spec.brick.module;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.spec.brick.container.HAPBrickContainer;
import com.nosliw.core.application.spec.common.task.HAPWithBrickTasks;

@HAPEntityWithAttribute
public interface HAPBlockModule extends HAPBrick, HAPWithBrickTasks{

	@HAPAttribute
	public static String COMMAND = "command";

	@HAPAttribute
	public static String PAGE = "page";

	HAPBrickContainer getCommands();

	HAPBrickContainer getPages();
	
}
