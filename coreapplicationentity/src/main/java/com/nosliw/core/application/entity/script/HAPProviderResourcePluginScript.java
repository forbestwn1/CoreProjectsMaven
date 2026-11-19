package com.nosliw.core.application.entity.script;

import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.resource.HAPFactoryResourceTypeId;
import com.nosliw.core.resource.HAPProviderResourcePluginImp;

@Component
public class HAPProviderResourcePluginScript extends HAPProviderResourcePluginImp{

	private HAPConfigureScript m_scriptConfigure;
	
	public HAPProviderResourcePluginScript(HAPConfigureScript scriptConfigure) {
		this.m_scriptConfigure = scriptConfigure;
		this.registerPlugin(HAPFactoryResourceTypeId.newInstance(HAPConstantShared.RUNTIME_RESOURCE_TYPE_SCRIPT), new HAPPluginResourceManagerScript(HAPConstantShared.RUNTIME_RESOURCE_TYPE_SCRIPT, this.m_scriptConfigure));
	}
	
}
