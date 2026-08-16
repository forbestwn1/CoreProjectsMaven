package com.nosliw.core.application.entity.brick;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPInfoBrickType;
import com.nosliw.core.application.HAPUtilityBrick;
import com.nosliw.core.resource.HAPInfoResourceIdNormalize;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.resource.HAPUtilityResourceId;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPUtilityOtherBrick {

	public static HAPBrick getBrickByResource(HAPInfoResourceIdNormalize normalizedResourceId, HAPManagerApplicationBrick brickMan, HAPRuntimeInfo runtimeInfo) {
		HAPBundleForBrick bundle = HAPUtilityOtherBundleForBrick.getBrickBundle(normalizedResourceId.getRootResourceId(), brickMan, runtimeInfo);
		return HAPUtilityBrick.getDescdentBrickLocal(bundle, normalizedResourceId.getPath(), HAPConstantShared.NAME_ROOTBRICK_MAIN);
	}

	public static HAPBrick getBrick(HAPEntityOrReference brickOrRef, HAPManagerApplicationBrick brickManager, HAPRuntimeInfo runtimeInfo) {
		HAPBrick out = null;
		String type = brickOrRef.getEntityOrReferenceType();
		if(type.equals(HAPConstantShared.BRICK)) {
			out = (HAPBrick)brickOrRef;
		}
		else if(type.equals(HAPConstantShared.RESOURCEID)) {
			out = HAPUtilityOtherBrick.getBrickByResource(HAPUtilityResourceId.normalizeResourceId((HAPResourceId)brickOrRef), brickManager, runtimeInfo);			
		}
		return out;
	}
	
	public static boolean isBrickTask(HAPIdBrickType brickTypeId, HAPManagerApplicationBrick brickMan) {
		HAPInfoBrickType brickTypeInfo = brickMan.getBrickTypeInfo(brickTypeId);
		if(brickTypeInfo!=null) {
			return brickTypeInfo.isTask();
		}
		return false;
	}

	public static String getBrickTaskType(HAPIdBrickType brickTypeId, HAPManagerApplicationBrick brickMan) {
		HAPInfoBrickType brickTypeInfo = brickMan.getBrickTypeInfo(brickTypeId);
		if(brickTypeInfo!=null) {
			return brickTypeInfo.getTaskType();
		}
		return null;
	}

}
