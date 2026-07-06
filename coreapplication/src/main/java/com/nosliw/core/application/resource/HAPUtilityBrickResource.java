package com.nosliw.core.application.resource;

import com.google.common.collect.Lists;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPUtilityBundleForExecute;
import com.nosliw.core.resource.HAPManagerResource;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPUtilityBrickResource {

	public static HAPBrick getResourceDataBrick(HAPResourceId resourceId, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		HAPResourceDataBrick brickResourceData = (HAPResourceDataBrick)resourceMan.getResources(Lists.asList(resourceId, new HAPResourceId[0]), runtimeInfo).getLoadedResource(resourceId).getResourceData();
		return brickResourceData.getBundle().getBrick();
	}
	
	public static HAPResourceDataBrick getResourceData(HAPResourceId resourceId, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		HAPResourceDataBrick brickResourceData = (HAPResourceDataBrick)resourceMan.getResources(Lists.asList(resourceId, new HAPResourceId[0]), runtimeInfo).getLoadedResource(resourceId).getResourceData();
		return brickResourceData;
	}

	public static HAPResourceDataBrick buildResourceDataBrick(HAPBundleForBrick bundle, String name, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		return new HAPResourceDataBrick(HAPUtilityBundleForExecute.toBundleExecutable(bundle, name));
	}
}
