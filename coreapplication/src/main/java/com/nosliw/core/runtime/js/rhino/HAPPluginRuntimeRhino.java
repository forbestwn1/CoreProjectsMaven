package com.nosliw.core.runtime.js.rhino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.gateway.HAPGatewayManager;
import com.nosliw.core.runtime.HAPPluginRuntime;
import com.nosliw.core.runtime.HAPRuntimeAdapterLoadResource;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.runtime.HAPRuntimeManager;
import com.nosliw.core.runtime.execute.HAPFactoryExecutorRuntime;
import com.nosliw.core.runtime.js.HAPRuntimeAdapterLoadResourceImp;
import com.nosliw.core.service.staticresource.HAPServiceStaticResource;

@Component
public class HAPPluginRuntimeRhino implements HAPPluginRuntime{

	@Autowired
	private HAPGatewayManager m_gatewayManager;

	@Autowired
	private HAPServiceStaticResource m_staticResourceService;

	@Override
	public HAPRuntimeInfo getRuntimeInfo() {  return HAPRuntimeManager.RUNTIME_JS_RHION;  }

	@Override
	public HAPFactoryExecutorRuntime getRuntimeExecutorFactory() {   return new HAPFactoryRuntimeRhino(this.m_gatewayManager);    }

	@Override
	public HAPRuntimeAdapterLoadResource getRuntimeLoadResourceAdapter() {   return new HAPRuntimeAdapterLoadResourceImp(this.m_staticResourceService);   }

}
