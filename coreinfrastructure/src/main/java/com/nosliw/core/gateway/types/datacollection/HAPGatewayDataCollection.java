package com.nosliw.core.gateway.types.datacollection;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.gateway.HAPGateway;

@HAPEntityWithAttribute
public interface HAPGatewayDataCollection extends HAPGateway{

	@HAPAttribute
	final public static String COMMAND_REQUEST_ERROR = "error";

	@HAPAttribute
	final public static String COMMAND_REQUEST_ERROR_TIME = "time";

	@HAPAttribute
	final public static String COMMAND_REQUEST_ERROR_CONTENT = "content";

	@HAPAttribute
	final public static String COMMAND_REQUEST_APPLICATION = "application";

	@HAPAttribute
	final public static String COMMAND_REQUEST_APPLICATION_TIME = "time";

	@HAPAttribute
	final public static String COMMAND_REQUEST_APPLICATION_RESOURCEID = "resourceid";

}
