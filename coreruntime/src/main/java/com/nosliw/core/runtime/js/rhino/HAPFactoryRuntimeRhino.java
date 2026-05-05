package com.nosliw.core.runtime.js.rhino;

import java.util.List;

import com.nosliw.core.gateway.HAPGatewayManager;
import com.nosliw.core.runtime.execute.HAPExecutorRuntime;
import com.nosliw.core.runtime.execute.HAPFactoryExecutorRuntime;

public class HAPFactoryRuntimeRhino implements HAPFactoryExecutorRuntime{

	private HAPGatewayManager m_gatewayManager;
	
	private List<HAPFactoryTaskRuntime> m_taskFactory;

	public HAPFactoryRuntimeRhino(List<HAPFactoryTaskRuntime> taskFactory, HAPGatewayManager gatewayManager) {
		this.m_gatewayManager = gatewayManager;
		this.m_taskFactory = taskFactory;
	}

	@Override
	public HAPExecutorRuntime newRuntimeExecutor() {
		return new HAPExecutorRuntimeImpRhino(this.m_taskFactory, this.m_gatewayManager);
	}

}
