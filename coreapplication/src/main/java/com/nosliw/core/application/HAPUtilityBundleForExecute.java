package com.nosliw.core.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.structure.HAPRootInStructure;
import com.nosliw.core.application.common.structure.HAPStructure;
import com.nosliw.core.application.valueport.HAPContainerValuePorts;
import com.nosliw.core.application.valueport.HAPGroupValuePorts;
import com.nosliw.core.application.valueport.HAPIdElement;
import com.nosliw.core.application.valueport.HAPIdRootElement;
import com.nosliw.core.application.valueport.HAPIdValuePort;
import com.nosliw.core.application.valueport.HAPIdValuePortInBundle;
import com.nosliw.core.application.valueport.HAPInfoValueStructure;
import com.nosliw.core.application.valueport.HAPValuePort;

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
		
		//figure out exported variables
		HAPIdBrickInBundle brickId = new HAPIdBrickInBundle();
		brickId.setIdPath(exportInfo.getPathFromRoot().toString());
		
		HAPContainerValuePorts valuePortContainer = out.getBrick().getExternalValuePorts();
		if(valuePortContainer!=null) {
			for(HAPGroupValuePorts group :  valuePortContainer.getValuePortGroups()) {
				for(HAPValuePort valuePort : group.getValuePorts()) {
					HAPIdValuePort valuePortId = new HAPIdValuePort(group.getId(), valuePort.getName());
					HAPIdValuePortInBundle valuePortIdInBundle = new HAPIdValuePortInBundle(brickId, HAPConstantShared.VALUEPORTGROUP_SIDE_EXTERNAL, valuePortId);
					for(HAPInfoValueStructure valueStructureInfo : valuePort.getValueStructureInfos()) {
						HAPStructure structure = bundleForBrick.getValueStructureDomain().getStructureDefinitionByRuntimeId(valueStructureInfo.getValueStructureId());
						Map<String, HAPRootInStructure> roots = structure.getRoots();
						for(String rootName : roots.keySet()) {
							out.addExportVariableInfo(rootName, new HAPIdElement(new HAPIdRootElement(valuePortIdInBundle, valueStructureInfo.getValueStructureId(), rootName), null));
						}
					}
				}
			}
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
