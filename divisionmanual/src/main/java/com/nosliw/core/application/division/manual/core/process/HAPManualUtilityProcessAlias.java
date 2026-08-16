package com.nosliw.core.application.division.manual.core.process;

import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPHandlerDownward;
import com.nosliw.core.application.brick.HAPUtilityBrick;
import com.nosliw.core.application.brick.HAPUtilityBundleForBrick;

public class HAPManualUtilityProcessAlias {

	public static void processBrickAlias(HAPManualContextProcessBrick processContext) {
		
		HAPManualUtilityBrickTraverse.traverseTree(processContext, new HAPHandlerDownward() {

			@Override
			public boolean processBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
				if(path.isEmpty()) {
					return true;
				}
				
				if(HAPUtilityBundleForBrick.getBrickFullPathInfo(path).getPath().isEmpty()) {
					return true;
				}
				
				HAPAttributeInBrick attr = HAPUtilityBrick.getDescendantAttribute(bundle, path);
				String alias = HAPUtilityEntityInfo.getAlias(attr.getInfo());
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
