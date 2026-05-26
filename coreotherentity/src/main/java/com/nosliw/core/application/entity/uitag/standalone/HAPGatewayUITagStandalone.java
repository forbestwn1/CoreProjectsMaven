package com.nosliw.core.application.entity.uitag.standalone;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;

@HAPEntityWithAttribute
@Component
public class HAPGatewayUITagStandalone {

	@HAPAttribute
	final public static String COMMAND_BUILD = "build";
	
	@HAPAttribute
	final public static String COMMAND_BUILD_UITAGID = "uiTagId";
	
	@HAPAttribute
	final public static String COMMAND_BUILD_DATADEFINITION = "dataDefinition";
	
	
	//build stand alone application 
	public HAPUITagStandaloneApplication buildStandAloneApplication(String uiTagId, Map<String, String> tagAttributes, HAPDataDefinition dataDefinition) {
		
	}
	
	
}
