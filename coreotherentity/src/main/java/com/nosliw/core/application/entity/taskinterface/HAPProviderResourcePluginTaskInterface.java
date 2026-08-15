package com.nosliw.core.application.entity.taskinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.resource.HAPFactoryResourceTypeId;
import com.nosliw.core.resource.HAPProviderResourcePluginImp;

@Component
public class HAPProviderResourcePluginTaskInterface extends HAPProviderResourcePluginImp{

	@Autowired
	private HAPTaskInterfaceConfigure m_taskInterfaceConfigure;
	
	public HAPProviderResourcePluginTaskInterface(HAPServiceParseEntity entityParseService) {
		this.registerPlugin(HAPFactoryResourceTypeId.newInstance(HAPConstantShared.RUNTIME_RESOURCE_TYPE_SERVICEINTERFACE), new HAPPluginResourceManagerTaskInterface(entityParseService, this.m_taskInterfaceConfigure));
	}
	
}
