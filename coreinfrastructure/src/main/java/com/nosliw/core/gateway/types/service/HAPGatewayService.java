package com.nosliw.core.gateway.types.service;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.gateway.HAPGateway;

@HAPEntityWithAttribute
public interface HAPGatewayService extends HAPGateway{

	@HAPAttribute
	final public static String COMMAND_REQUEST = "request";

	@HAPAttribute
	final public static String COMMAND_REQUEST_QUERY = "query";

	@HAPAttribute
	final public static String COMMAND_REQUEST_PARMS = "parms";

	@HAPAttribute
	final public static String COMMAND_SEARCHDEFINITION = "searchDefinition";

	@HAPAttribute
	final public static String COMMAND_SEARCHDEFINITION_QUERY = "query";

}
