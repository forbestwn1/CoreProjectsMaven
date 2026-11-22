package com.nosliw.core.application.common.interactive;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.interfac.HAPEntityOrReference;

@HAPEntityWithAttribute
public interface HAPWithBlockInteractiveTask{

	@HAPAttribute
	public static String TASKINTERFACE = "taskInterface";
	
	public HAPEntityOrReference getTaskInterface();

	public void setTaskInterface(HAPEntityOrReference taskInterface);

}
