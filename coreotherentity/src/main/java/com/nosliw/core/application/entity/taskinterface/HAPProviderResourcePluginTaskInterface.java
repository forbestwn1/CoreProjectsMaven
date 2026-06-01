package com.nosliw.core.application.entity.taskinterface;

import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.resource.HAPFactoryResourceTypeId;
import com.nosliw.core.resource.HAPProviderResourcePluginImp;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@Component
public class HAPProviderResourcePluginTaskInterface extends HAPProviderResourcePluginImp{

	public HAPProviderResourcePluginTaskInterface(HAPServiceParseEntity entityParseService) {
		this.registerPlugin(HAPFactoryResourceTypeId.newInstance(HAPConstantShared.RUNTIME_RESOURCE_TYPE_SERVICEINTERFACE), new HAPPluginResourceManagerTaskInterface(entityParseService));
	}
	
}
