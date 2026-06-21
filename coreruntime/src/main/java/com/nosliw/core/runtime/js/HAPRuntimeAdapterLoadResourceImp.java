package com.nosliw.core.runtime.js;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.core.gateway.HAPGatewayOutput;
import com.nosliw.core.resource.HAPResource;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.resource.HAPResourceInfo;
import com.nosliw.core.runtime.HAPRuntimeAdapterLoadResource;
import com.nosliw.core.service.staticresource.HAPServiceStaticResource;

public class HAPRuntimeAdapterLoadResourceImp implements HAPRuntimeAdapterLoadResource{

	private HAPServiceStaticResource m_staticResourceService;

	public HAPRuntimeAdapterLoadResourceImp(HAPServiceStaticResource staticResourceService) {
		this.m_staticResourceService = staticResourceService;
	}
	
	@Override
	public Object buildLoadResourceData(Map<HAPResourceId, HAPResourceInfo> resourcesInfo, List<HAPResource> resources) {
		List<HAPResourceId> resourceIds = new ArrayList<HAPResourceId>();
		List<HAPJSScriptInfo> scriptsInfo = new ArrayList<HAPJSScriptInfo>();
		for(HAPResource resource : resources){
			//build scripts info
			HAPResourceInfo resourceInfo = resourcesInfo.get(resource.getId());
			scriptsInfo.addAll(HAPUtilityRuntimeJsScriptResource.buildScriptForResource(resourceInfo, resource, m_staticResourceService));
			resourceIds.add(resource.getId());
		}
		return new HAPGatewayOutput(scriptsInfo, resourceIds);
	}

}
