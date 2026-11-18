package com.nosliw.core.xxx.application.division.manual.core.b;

import com.nosliw.common.path.HAPComplexPath;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.util.HAPUtilityBrick;
import com.nosliw.core.application.division.manual.core.HAPInfoTreeNode;
import com.nosliw.core.application.division.manual.core.HAPManualUtilityBrick;

public class HAPManualExeUtilityBrick {

	


	public static HAPBrick getDescdentBrickLocal(HAPBundleForBrick bundle, HAPInfoTreeNode treeNodeInfo) {
		HAPComplexPath pathInfo = HAPManualUtilityBrick.getBrickFullPathInfo(treeNodeInfo);
		return HAPUtilityBrick.getDescdentBrickLocal(bundle, pathInfo.getPath());
	}

}
