package com.nosliw.core.application.entity.js.library;

import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.resource.HAPFactoryResourceTypeId;
import com.nosliw.core.resource.HAPProviderResourcePluginImp;
import com.nosliw.core.service.HAPServiceStaticResource;

@Component
public class HAPProviderResourcePluginJSLibrary extends HAPProviderResourcePluginImp{

	HAPServiceStaticResource m_staticResourceService;
	
	public HAPProviderResourcePluginJSLibrary(HAPServiceStaticResource staticResourceService) {
		this.m_staticResourceService = staticResourceService;
		this.registerPlugin(HAPFactoryResourceTypeId.newInstance(HAPConstantShared.RUNTIME_RESOURCE_TYPE_JSLIBRARY), new HAPPluginResourceManagerJSLibrary(this.m_staticResourceService));
	}
}
