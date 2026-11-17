package com.nosliw.core.application.entity.brick;

import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.HAPUtilityBrickId;
import com.nosliw.core.application.common.resource.HAPUtilityExport;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPUtilityOtherBundleForBrick {

	public static HAPBundleForBrick getBrickBundle(HAPResourceIdSimple resourceId, HAPManagerApplicationBrick brickMan, HAPRuntimeInfo runtimeInfo) {
		HAPBundleForBrick bundle = brickMan.getBrickBundle(HAPUtilityBrickId.fromResourceId2BrickId(resourceId), runtimeInfo);
		if(brickMan.getBundleConfigure().isExport()) {
			String exportPath = brickMan.getBundleConfigure().getExportPath();
			if(exportPath!=null) {
				HAPUtilityExport.exportBundle(resourceId, bundle, HAPUtilityFileNio.buildPath(exportPath));
			}
		}
		return bundle;
	}

}
