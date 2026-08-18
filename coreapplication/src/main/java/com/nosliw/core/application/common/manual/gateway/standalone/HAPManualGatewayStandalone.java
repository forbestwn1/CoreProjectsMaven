package com.nosliw.core.application.common.manual.gateway.standalone;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;

@HAPEntityWithAttribute
public interface HAPManualGatewayStandalone {

	@HAPAttribute
	static public final String COMMAND_BUILD = "build";

	@HAPAttribute
	static public final String PARMS_REQUEST = "request";

}

