package com.nosliw.core.application.brick.spec.task.flow;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainer;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveTask;

@HAPEntityWithAttribute
public interface HAPBlockTaskFlowFlow extends HAPBrick, HAPWithBlockInteractiveTask{

	@HAPAttribute
	public static final String START = "start";

	@HAPAttribute
	public static final String ACTIVITY = "activity";

	
	HAPTaskFlowNext getStart();
	
	HAPBrickContainer getActivities();
	
}
