package com.nosliw.core.runtime.js.rhino;

import com.nosliw.core.gateway.HAPGatewayManager;
import com.nosliw.core.runtime.execute.HAPExecutorRuntime;
import com.nosliw.core.runtime.execute.HAPFactoryExecutorRuntime;

public class HAPFactoryRuntimeRhino implements HAPFactoryExecutorRuntime{

	private HAPGatewayManager m_gatewayManager;

	public HAPFactoryRuntimeRhino(HAPGatewayManager gatewayManager) {
		this.m_gatewayManager = gatewayManager;
	}

	@Override
	public HAPExecutorRuntime newRuntimeExecutor() {
		return new HAPExecutorRuntimeImpRhino(this.m_gatewayManager);
	}

}
