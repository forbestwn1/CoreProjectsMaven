package com.nosliw.core.application.common.command;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;

public class HAPCommandDefinition extends HAPEntityInfoImp{

	@HAPAttribute
	public final static String INTERFACE = "interface"; 
	
	//command interface
	private HAPInteractiveTask m_taskInterface;
	
}
