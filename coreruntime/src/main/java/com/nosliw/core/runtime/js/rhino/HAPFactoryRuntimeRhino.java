package com.nosliw.core.runtime.js.rhino;

import java.util.List;

import com.nosliw.core.application.entity.gateway.HAPGatewayManager;
import com.nosliw.core.runtime.execute.HAPExecutorRuntime;
import com.nosliw.core.runtime.execute.HAPFactoryExecutorRuntime;

public class HAPFactoryRuntimeRhino implements HAPFactoryExecutorRuntime{

	private HAPConfigureRhinoRuntime m_rhinoRuntimeConfigure;
	
	private HAPGatewayManager m_gatewayManager;
	
	private List<HAPFactoryTaskRuntime> m_taskFactory;

	public HAPFactoryRuntimeRhino(List<HAPFactoryTaskRuntime> taskFactory, HAPConfigureRhinoRuntime rhinoRuntimeConfigure, HAPGatewayManager gatewayManager) {
		this.m_rhinoRuntimeConfigure = rhinoRuntimeConfigure;
		this.m_gatewayManager = gatewayManager;
		this.m_taskFactory = taskFactory;
	}

	@Override
	public HAPExecutorRuntime newRuntimeExecutor() {
		return new HAPExecutorRuntimeImpRhino(this.m_taskFactory, this.m_rhinoRuntimeConfigure, this.m_gatewayManager);
	}

}
