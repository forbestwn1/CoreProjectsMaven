package com.nosliw.core.runtime;

import com.nosliw.core.runtime.execute.HAPFactoryExecutorRuntime;

public interface HAPPluginRuntime {

	HAPRuntimeInfo getRuntimeInfo();
	
	HAPFactoryExecutorRuntime getRuntimeExecutorFactory();
	
	HAPRuntimeAdapterLoadResource getRuntimeLoadResourceAdapter();
	
}
