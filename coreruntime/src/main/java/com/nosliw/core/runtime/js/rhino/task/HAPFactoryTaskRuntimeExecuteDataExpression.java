package com.nosliw.core.runtime.js.rhino.task;

import org.springframework.stereotype.Component;

import com.nosliw.core.runtime.execute.HAPInfoRuntimeTask;
import com.nosliw.core.runtime.execute.HAPTaskRuntime;
import com.nosliw.core.runtime.js.rhino.HAPFactoryTaskRuntime;

@Component
public class HAPFactoryTaskRuntimeExecuteDataExpression implements HAPFactoryTaskRuntime{

	public HAPFactoryTaskRuntimeExecuteDataExpression() {}
	
	@Override
	public String getRuntimeType() {   return  HAPInfoRuntimeTask.RUNTIMETASK_TYPE_EXECUTEDATAEXPRESSION;   }

	@Override
	public HAPTaskRuntime createRuntimeTask(HAPInfoRuntimeTask taskInfo) {
		return new HAPRuntimeTaskExecuteDataExpressionRhino(taskInfo);
	}

}
