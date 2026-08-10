package com.nosliw.core.runtime.execute;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public interface HAPExecutorRuntime {

	public static final boolean isDemo = false;

	HAPRuntimeInfo getRuntimeInfo();
	
	//async request
	void executeTask(HAPInfoRuntimeTask task);

	//sync request
	HAPServiceData executeTaskSync(HAPInfoRuntimeTask task);

	//async request
	void executeTask(HAPTaskRuntime task);

	//sync request
	HAPServiceData executeTaskSync(HAPTaskRuntime task);

	void close();
	
	void start();

}
