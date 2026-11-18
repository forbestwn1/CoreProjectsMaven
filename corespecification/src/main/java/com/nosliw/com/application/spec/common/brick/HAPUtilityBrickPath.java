package com.nosliw.com.application.spec.common.brick;

import com.nosliw.com.application.spec.common.task.HAPUtilityTask;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.HAPUtilityBundleForBrick;

public class HAPUtilityBrickPath {

	public static HAPPath normalizeBrickPath(HAPPath path, String brickRootNameIfNotProvided, boolean processEnd, HAPBundleForBrick currentBundle) {
		//branch
		path = new HAPPath(HAPUtilityBundleForBrick.normalizePathWithBranch(path.getPath(), brickRootNameIfNotProvided));
		
		//task
		return HAPUtilityTask.normalizeBrickPathFroTask(path, processEnd, currentBundle);
	}
	
}
