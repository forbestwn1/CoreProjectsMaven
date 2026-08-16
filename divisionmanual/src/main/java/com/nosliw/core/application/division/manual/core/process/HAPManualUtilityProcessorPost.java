package com.nosliw.core.application.division.manual.core.process;

import java.util.Set;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPHandlerDownward;
import com.nosliw.core.application.brick.HAPUtilityBrick;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.entity.brick.HAPUtilityOtherBrickTraverse;

public class HAPManualUtilityProcessorPost {

	public static void process(HAPManualContextProcessBrick processContext) {
		cleanupEmptyValueStructure(processContext);
	}
	
	private static void cleanupEmptyValueStructure(HAPManualContextProcessBrick processContext) {
		Set<String> vsIds = processContext.getCurrentBundle().getValueStructureDomain().cleanupEmptyValueStructure();

		HAPUtilityOtherBrickTraverse.traverseTreeWithLocalBrick(processContext.getCurrentBundle(), processContext.getRootBrickName(), new HAPHandlerDownward() {

			@Override
			public boolean processBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
				HAPManualBrickImp brick = (HAPManualBrickImp)HAPUtilityBrick.getDescdentBrickLocal(bundle, path);
				brick.getManualValueContext().cleanValueStucture(vsIds);
				brick.getOtherExternalValuePortContainer().cleanValueStucture(vsIds);
				brick.getOtherInternalValuePortContainer().cleanValueStucture(vsIds);
				return true;
			}

			@Override
			public void postProcessBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
			}

		}, processContext.getBrickManager(), null);
	}
	

	
}
