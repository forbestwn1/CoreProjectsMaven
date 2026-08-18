package com.nosliw.core.application.common.manual.gateway.compile;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;

@HAPEntityWithAttribute
public interface HAPManualGatewayCompile {

	@HAPAttribute
	static public final String COMMAND_COMPILE = "compile";
	@HAPAttribute
	static public final String PARMS_CONTENT = "content";
	
}
