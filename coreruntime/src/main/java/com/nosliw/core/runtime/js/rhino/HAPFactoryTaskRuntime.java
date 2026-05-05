package com.nosliw.core.runtime.js.rhino;

import com.nosliw.core.runtime.execute.HAPInfoRuntimeTask;
import com.nosliw.core.runtime.execute.HAPTaskRuntime;

public interface HAPFactoryTaskRuntime {

    String getRuntimeType();
	
	HAPTaskRuntime createRuntimeTask(HAPInfoRuntimeTask taskInfo);
	
	
}
