package com.nosliw.core.application.entity.configure;

import java.nio.file.Path;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.resource.HAPPluginResourceManager;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPPluginResourceManagerConfigure implements HAPPluginResourceManager{

	private HAPCongiureConfigure m_configureConfigure;
	
	public HAPPluginResourceManagerConfigure(HAPCongiureConfigure configureConfigure) {
		this.m_configureConfigure = configureConfigure;
	}

	@Override
	public HAPResourceDataOrWrapper getResourceData(HAPResourceIdSimple simpleResourceId, HAPRuntimeInfo runtimeInfo) {
		String configureId = simpleResourceId.getId();
		
		Path configureFile = HAPUtilityFileNio.buildPath(m_configureConfigure.getPath(), configureId + ".json");
		String configureStr = HAPUtilityFileNio.readFile(configureFile);
		
		HAPResourceDataConfigure configureResourceData = new HAPResourceDataConfigure(new JSONObject(configureStr));
		return configureResourceData;
	}

}
