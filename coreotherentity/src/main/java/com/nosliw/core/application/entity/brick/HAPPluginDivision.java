package com.nosliw.core.application.entity.brick;

import java.util.Set;

import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public interface HAPPluginDivision {

	String getDivisionName();
	
	HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo);

	//what brick type related with this division
	Set<HAPIdBrickType> getBrickTypes();

}
