package com.nosliw.core.application.entity.script;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.HAPIdBrick;
import com.nosliw.core.application.common.brick.HAPIdBrickType;
import com.nosliw.core.application.common.brick.HAPWrapperBrickRoot;
import com.nosliw.core.application.entity.brick.HAPPluginDivision;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.imp.basic.HAPBasicBrickScript;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPPluginDivisionScript implements HAPPluginDivision{

	@Autowired
	private HAPConfigureDivisionScript m_scriptConfigure;

	public HAPPluginDivisionScript() {
	}
	
	@Override
	public String getDivisionName() {  return HAPConstantShared.BRICK_DIVISION_SCRIPT;  }

	@Override
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPIdBrickType brickTypeId = brickId.getBrickTypeId();
		
		Path scriptFile = HAPUtilityFileNio.buildPath(HAPUtilityFileNio.buildPath(this.m_scriptConfigure.getDefinitionPath()), brickTypeId.getBrickType(), brickId.getId() + ".js");
		String script = HAPUtilityFileNio.readFile(scriptFile);

		HAPBasicBrickScript scriptBrick = new HAPBasicBrickScript(brickTypeId, this.getDivisionName());
		scriptBrick.setScript(script);
		
		HAPBundleForBrick bundle = HAPBundleForBrick.newBundleForBrick();
		bundle.setMainBrickWrapper(new HAPWrapperBrickRoot(scriptBrick));
		return bundle;
	}

	@Override
	public Set<HAPIdBrickType> getBrickTypes() {
		Set<HAPIdBrickType> out = new HashSet<HAPIdBrickType>();
		out.add(HAPEnumBrickType.DECORATIONSCRIPT_100);
		return out;
	}

}
