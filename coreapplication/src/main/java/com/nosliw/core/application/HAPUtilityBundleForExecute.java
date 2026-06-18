package com.nosliw.core.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;

public class HAPUtilityBundleForExecute {

	public static HAPBundleForExecute toBundleExecutable(HAPBundleForBrick bundleForBrick, String exportName) {
		Map<String, HAPBrick> supportBricks = new LinkedHashMap<>();
		for(String branchName : bundleForBrick.getBranchNames()) {
			supportBricks.put(branchName, bundleForBrick.getBranchBrickWrapper(branchName).getBrick());
		}
		
		HAPBundleForExecute bundleForExe = new HAPBundleForExecute(bundleForBrick.getMainBrickWrapper().getBrick(), supportBricks, HAPUtilityBundleForExecute.getBrickExportInfo(bundleForBrick, null), bundleForBrick.getAliasMappings(), bundleForBrick.getValueStructureDomain());
		return bundleForExe;
	}
	
	public static HAPInfoExportBrick getBrickExportInfo(HAPBundleForBrick bundle, String name) {
		if(name==null) {
			name = HAPConstantShared.NAME_DEFAULT;
		}
		HAPInfoExportBrick exportInfo = null;
		for(HAPInfoExportBrick ei : bundle.getExportResourceInfos()) {
			if(name.equals(ei.getName())) {
				exportInfo = ei;
				break;
			}
		}
		return exportInfo;
	}
	
	
}
