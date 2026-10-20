package com.nosliw.core.application.brick;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;

@HAPEntityWithAttribute
public interface HAPWithBrickReferences {

	@HAPAttribute
	public static String BRICKREFERENCES = "brickReferences";

	Map<String, HAPIdBrickInBundle> getBrickReferences();
	
	void addBrickReference(String name, HAPIdBrickInBundle brickRef);
	
}
