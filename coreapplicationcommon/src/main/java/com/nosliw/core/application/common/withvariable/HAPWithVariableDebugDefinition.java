package com.nosliw.core.application.common.withvariable;

import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;

@HAPEntityWithAttribute
public interface HAPWithVariableDebugDefinition {

	@HAPAttribute
	public static String VARIABLE = "variable";

	List<String> getVariables();
	
}
