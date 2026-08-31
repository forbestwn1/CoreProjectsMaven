package com.nosliw.core.application.division.manual.core.process;

import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.path.HAPComplexPath;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPHandlerDownward;
import com.nosliw.core.application.brick.HAPUtilityBrick;
import com.nosliw.core.application.brick.HAPUtilityBundleForBrick;
import com.nosliw.core.application.brick.HAPWrapperBrickRoot;

public class HAPManualUtilityProcessAlias {

	public static void processBrickAlias(HAPManualContextProcessBrick processContext) {
		
		HAPManualUtilityBrickTraverse.traverseTree(processContext, new HAPHandlerDownward() {

			@Override
			public boolean processBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
				if(path.isEmpty()) {
					return true;
				}
				
				HAPComplexPath complexPath = HAPUtilityBundleForBrick.getBrickFullPathInfo(path);
				HAPInfo info = null;
				if(complexPath.getPath().isEmpty()) {
					//root brick
					HAPWrapperBrickRoot brickRootWrapper = bundle.getRootBrickWrapper(complexPath.getRoot());
					info = brickRootWrapper.getInfo();
				}
				else {
					HAPAttributeInBrick attr = HAPUtilityBrick.getDescendantAttribute(bundle, path);
					info = attr.getInfo();
				}
				
				String alias = HAPUtilityEntityInfo.getAlias(info);
				if(alias!=null) {
					if(processContext.getCurrentBundle().getBrickPathByAlias(alias)!=null) {
						throw new RuntimeException();
					}
					processContext.getCurrentBundle().addAliasMapping(alias, path);
				}
				
				return true;
			}

			@Override
			public void postProcessBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
			}

		}, null);
	}
	
}
