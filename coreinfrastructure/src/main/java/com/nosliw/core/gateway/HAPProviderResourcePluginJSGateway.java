package com.nosliw.core.gateway;

import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.resource.HAPFactoryResourceTypeId;
import com.nosliw.core.resource.HAPProviderResourcePluginImp;

@Component
public class HAPProviderResourcePluginJSGateway extends HAPProviderResourcePluginImp{

	public HAPProviderResourcePluginJSGateway() {
		this.registerPlugin(HAPFactoryResourceTypeId.newInstance(HAPConstantShared.RUNTIME_RESOURCE_TYPE_JSGATEWAY), new HAPPluginResourceManagerJSGateway());
	}

}
