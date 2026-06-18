package com.nosliw.core.application.resource;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPInfoExportBrick;
import com.nosliw.core.application.HAPResultBrickDescentValue;
import com.nosliw.core.application.HAPUtilityBrick;
import com.nosliw.core.application.HAPUtilityBundleForExecute;
import com.nosliw.core.application.HAPWrapperBrickRoot;
import com.nosliw.core.resource.HAPManagerResource;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.resource.HAPUtilityResource;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPUtilityBrickResource {

	public static HAPBrick getResourceDataBrick(HAPResourceId resourceId, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		HAPResourceDataBrick brickResourceData = (HAPResourceDataBrick)resourceMan.getResources(Lists.asList(resourceId, new HAPResourceId[0]), runtimeInfo).getLoadedResource(resourceId).getResourceData();
		return brickResourceData.getBrick();
	}
	public static HAPResourceDataBrick getResourceData(HAPResourceId resourceId, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		HAPResourceDataBrick brickResourceData = (HAPResourceDataBrick)resourceMan.getResources(Lists.asList(resourceId, new HAPResourceId[0]), runtimeInfo).getLoadedResource(resourceId).getResourceData();
		return brickResourceData;
	}


	public static HAPResourceDataBrick getExportResourceData(HAPBundleForBrick bundle, String name, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		HAPInfoExportBrick exportInfo = HAPUtilityBundleForExecute.getBrickExportInfo(bundle, name);
		
		HAPResourceDataBrick out = null;
		HAPResultBrickDescentValue brickResult = HAPUtilityBrick.getDescdentBrickResult(bundle, exportInfo.getPathFromRoot(), HAPConstantShared.NAME_ROOTBRICK_MAIN);
		if(brickResult.getBrick()!=null) {
			Map<String, HAPBrick> suportBricks = new LinkedHashMap<String, HAPBrick>();
			Map<String, HAPWrapperBrickRoot> branches = bundle.getBranchBrickWrappers();
			for(String n : branches.keySet()) {
				suportBricks.put(n, branches.get(n).getBrick());
			}
			
			out = new HAPResourceDataBrick(brickResult.getBrick(), suportBricks, exportInfo, bundle.getAliasMappings(), bundle.getValueStructureDomain());
		}
		else {
			out = (HAPResourceDataBrick)HAPUtilityResource.getResource(brickResult.getResourceId(), resourceMan, runtimeInfo).getResourceData();
		}
		return out;
	}

}
