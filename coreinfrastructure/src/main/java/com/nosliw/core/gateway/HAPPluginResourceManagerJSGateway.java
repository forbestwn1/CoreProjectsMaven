package com.nosliw.core.gateway;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.resource.HAPPluginResourceManager;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceHelper;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPPluginResourceManagerJSGateway implements HAPPluginResourceManager{

	public HAPPluginResourceManagerJSGateway() {
		HAPResourceHelper.getInstance().registerResourceId(HAPConstantShared.RUNTIME_RESOURCE_TYPE_JSGATEWAY, HAPResourceIdJSGateway.class, HAPJSGatewayId.class);
	}

	
	@Override
	public HAPResourceDataOrWrapper getResourceData(HAPResourceIdSimple simpleResourceId, HAPRuntimeInfo runtimeInfo) {
		return new HAPResourceDataJSGateway(simpleResourceId.getCoreIdLiterate());
	}

}
