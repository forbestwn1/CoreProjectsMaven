package com.nosliw.core.application.entity.script;

import java.nio.file.Path;

import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.resource.HAPPluginResourceManager;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPPluginResourceManagerScript implements HAPPluginResourceManager{

	private String m_resourceType;
	
	private HAPConfigureScript m_scriptConfigure;
	
	public HAPPluginResourceManagerScript(String resourceType, HAPConfigureScript scriptConfigure) {
		this.m_resourceType = resourceType;
		this.m_scriptConfigure = scriptConfigure;
	}

	@Override
	public HAPResourceDataOrWrapper getResourceData(HAPResourceIdSimple simpleResourceId, HAPRuntimeInfo runtimeInfo) {
		String scriptId = simpleResourceId.getId();
		Path scriptPath = HAPUtilityFileNio.buildPath(HAPUtilityFileNio.buildPath(this.m_scriptConfigure.getPath()), simpleResourceId.getResourceTypeId().getResourceType(), scriptId + ".js");
		String script = HAPUtilityFileNio.readFile(scriptPath);
		
		HAPResourceDataScript scriptBrick = new HAPResourceDataScript();
		scriptBrick.setScript(script);
		
		return scriptBrick;
	}

}
