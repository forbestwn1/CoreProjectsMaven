package com.nosliw.core.resource;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;

@HAPEntityWithAttribute
public interface HAPResourceDataJSValue extends HAPResourceData{

	@HAPAttribute
	public static String VALUE = "value";
	
	String getValue();
}
