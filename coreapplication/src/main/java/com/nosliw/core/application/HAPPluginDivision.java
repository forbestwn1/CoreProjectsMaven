package com.nosliw.core.application;

import java.util.Set;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public interface HAPPluginDivision {

	String getDivisionName();
	
	HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo);

	//what brick type related with this division
	Set<HAPIdBrickType> getBrickTypes();

	HAPBrick deserializeBrick(Object obj, HAPSerializationFormat format);

}
