package com.nosliw.core.application.common.uitag;

import com.nosliw.common.constant.HAPAttribute;

public interface HAPGatewayUITag {

	@HAPAttribute
	final public static String COMMAND_GETTAG = "getTag";
	@HAPAttribute
	final public static String COMMAND_GETTAG_NAME = "name";
	@HAPAttribute
	final public static String COMMAND_GETTAG_VERSION = "version";

	@HAPAttribute
	final public static String COMMAND_GETDEFAULTTAG = "getDefaultTag";
	@HAPAttribute
	final public static String COMMAND_GETDEFAULTTAG_CRITERIA = "criteria";

	@HAPAttribute
	final public static String COMMAND_QUERYTAG = "queryTag";
	@HAPAttribute
	final public static String COMMAND_QUERYTAG_CRITERIA = "criteria";

	@HAPAttribute
	final public static String COMMAND_CLEARCHACHE = "clearCache";

}
