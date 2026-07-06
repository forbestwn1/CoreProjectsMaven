package com.nosliw.core.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;

public class HAPUtilityBundleForExecute {

	public static HAPBundleForExecute toBundleExecutable(HAPBundleForBrick bundleForBrick, String exportName) {
		HAPBundleForExecute out = new HAPBundleForExecute();

		HAPInfoExportBrick exportInfo = HAPUtilityBundleForExecute.getBrickExportInfo(bundleForBrick, exportName);
		HAPResultBrickDescentValue brickResult = HAPUtilityBrick.getDescdentBrickResult(bundleForBrick, exportInfo.getPathFromRoot(), HAPConstantShared.NAME_ROOTBRICK_MAIN);
		out.setBrick(brickResult.getBrick());
		out.setExportBrickInfo(exportInfo);
		out.addAliasMappings(bundleForBrick.getAliasMappings());
		out.setValueStructureDomain(bundleForBrick.getValueStructureDomain());
		
		Map<String, HAPBrick> suportBricks = new LinkedHashMap<String, HAPBrick>();
		Map<String, HAPWrapperBrickRoot> branches = bundleForBrick.getBranchBrickWrappers();
		for(String n : branches.keySet()) {
			out.addSupportBrick(n, branches.get(n).getBrick());
			suportBricks.put(n, branches.get(n).getBrick());
		}
		return out;
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
