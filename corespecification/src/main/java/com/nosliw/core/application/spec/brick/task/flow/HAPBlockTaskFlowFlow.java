package com.nosliw.core.application.spec.brick.task.flow;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveTask;
import com.nosliw.core.application.spec.brick.container.HAPBrickContainer;

@HAPEntityWithAttribute
public interface HAPBlockTaskFlowFlow extends HAPBrick, HAPWithBlockInteractiveTask{

	@HAPAttribute
	public static final String START = "start";

	@HAPAttribute
	public static final String ACTIVITY = "activity";

	
	HAPTaskFlowNext getStart();
	
	HAPBrickContainer getActivities();
	
}
