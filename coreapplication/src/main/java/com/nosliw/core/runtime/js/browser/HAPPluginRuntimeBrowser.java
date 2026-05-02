package com.nosliw.core.runtime.js.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.runtime.HAPPluginRuntime;
import com.nosliw.core.runtime.HAPRuntimeAdapterLoadResource;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.runtime.HAPRuntimeManager;
import com.nosliw.core.runtime.execute.HAPFactoryExecutorRuntime;
import com.nosliw.core.runtime.js.HAPRuntimeAdapterLoadResourceImp;
import com.nosliw.core.service.staticresource.HAPServiceStaticResource;

@Component
public class HAPPluginRuntimeBrowser implements HAPPluginRuntime{

	@Autowired
	private HAPServiceStaticResource m_staticResourceService;

	@Override
	public HAPRuntimeInfo getRuntimeInfo() {    return HAPRuntimeManager.RUNTIME_JS_BROWSER;   }

	@Override
	public HAPFactoryExecutorRuntime getRuntimeExecutorFactory() {
		return null;
	}

	@Override
	public HAPRuntimeAdapterLoadResource getRuntimeLoadResourceAdapter() {   return new HAPRuntimeAdapterLoadResourceImp(this.m_staticResourceService);   }

}
