package com.nosliw.core.application.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForExecute;
import com.nosliw.core.application.HAPDomainValueStructure;
import com.nosliw.core.application.HAPInfoExportBrick;
import com.nosliw.core.resource.HAPResourceData;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.resource.HAPWithResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
public class HAPResourceDataBrick extends HAPBundleForExecute implements HAPResourceData, HAPWithResourceDependency{

	public HAPResourceDataBrick(HAPBrick brick, Map<String, HAPBrick> supportBricks, HAPInfoExportBrick exportBrickInfo, Map<String, HAPPath> aliasMapping, HAPDomainValueStructure valueStructureDomain) {
		super(brick, supportBricks, exportBrickInfo, aliasMapping, valueStructureDomain);
	}
	
	@Override
	public HAPResourceDataOrWrapper getDescendant(HAPPath path) {
		throw new RuntimeException();
	}

	@Override
	public void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo) {
		this.getBrick().buildResourceDependency(dependency, runtimeInfo);
	}

	@Override
	public List<HAPResourceDependency> getResourceDependency(HAPRuntimeInfo runtimeInfo) {
		List<HAPResourceDependency> out = new ArrayList<HAPResourceDependency>();
		this.buildResourceDependency(out, runtimeInfo);
		return out;
	}

}
