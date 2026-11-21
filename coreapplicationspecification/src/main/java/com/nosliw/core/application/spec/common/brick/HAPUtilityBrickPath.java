package com.nosliw.core.application.spec.common.brick;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.util.HAPUtilityBundleForBrick;
import com.nosliw.core.application.spec.common.task.HAPUtilityTask;

public class HAPUtilityBrickPath {

	public static HAPPath normalizeBrickPath(HAPPath path, String brickRootNameIfNotProvided, boolean processEnd, HAPBundleForBrick currentBundle) {
		//branch
		path = new HAPPath(HAPUtilityBundleForBrick.normalizePathWithBranch(path.getPath(), brickRootNameIfNotProvided));
		
		//task
		return HAPUtilityTask.normalizeBrickPathFroTask(path, processEnd, currentBundle);
	}
	
}
