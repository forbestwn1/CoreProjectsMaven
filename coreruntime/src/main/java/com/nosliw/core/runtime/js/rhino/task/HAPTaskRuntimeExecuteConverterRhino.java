package com.nosliw.core.runtime.js.rhino.task;

import java.util.List;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPInfoRuntimeTaskExecuteConverter;
import com.nosliw.core.data.HAPTaskRuntimeImp;
import com.nosliw.core.resource.HAPResourceInfo;
import com.nosliw.core.runtime.execute.HAPExecutorRuntime;
import com.nosliw.core.runtime.execute.HAPInfoRuntimeTask;
import com.nosliw.core.runtime.execute.HAPRunTaskEventListener;
import com.nosliw.core.runtime.execute.HAPTaskRuntime;
import com.nosliw.core.runtime.js.rhino.HAPExecutorRuntimeImpRhino;

public class HAPTaskRuntimeExecuteConverterRhino extends HAPTaskRuntimeImp{

	HAPInfoRuntimeTaskExecuteConverter m_taskInfo;
	
	public HAPTaskRuntimeExecuteConverterRhino(HAPInfoRuntimeTask taskInfo) {
		this.m_taskInfo = (HAPInfoRuntimeTaskExecuteConverter)taskInfo;
	}

	public HAPInfoRuntimeTaskExecuteConverter getTaskInfo() {     return this.m_taskInfo;     }
	
	@Override
	public Class getResultDataType() {	return HAPData.class;	}
	
	@Override
	public HAPTaskRuntime execute(HAPExecutorRuntime runtime){
		try{
			HAPExecutorRuntimeImpRhino rhinoRuntime = (HAPExecutorRuntimeImpRhino)runtime;
			
			//prepare resources for data operation in the runtime (resource and dependency)
			//execute expression after load required resources
			List<HAPResourceInfo> resourcesId =	HAPUtilityExpressionResource.discoverResourceRequirement(this.m_taskInfo.getMatchers(),rhinoRuntime.getRuntimeEnvironment().getResourceManager(), runtime.getRuntimeInfo());
			
			HAPTaskRuntime loadResourcesTask = new HAPTaskRuntimeLoadResourcesRhino(resourcesId);
			loadResourcesTask.registerListener(new HAPRunTaskEventListenerInner(this, rhinoRuntime));
			return loadResourcesTask;
		}
		catch(Exception e){
			this.finish(HAPServiceData.createFailureData(e, ""));
			e.printStackTrace();
		}
		return null;
	}
	
	class HAPRunTaskEventListenerInner implements HAPRunTaskEventListener{
		private HAPTaskRuntimeExecuteConverterRhino m_parent;
		private HAPExecutorRuntimeImpRhino m_runtime;
		
		public HAPRunTaskEventListenerInner(HAPTaskRuntimeExecuteConverterRhino parent, HAPExecutorRuntimeImpRhino runtime){
			this.m_parent = parent;
			this.m_runtime = runtime;
		}
		
		@Override
		public void finish(HAPTaskRuntime task) {
			HAPServiceData resourceTaskResult = task.getResult();
			if(resourceTaskResult.isSuccess()){
				//after resource loaded, execute expression
				try{
					HAPJSScriptInfo scriptInfo = HAPUtilityRuntimeJSScriptTask.buildRequestScriptForExecuteDataConvertTask(this.m_parent, this.m_runtime);
					this.m_runtime.loadTaskScript(scriptInfo, m_parent.getTaskId());
				}
				catch(Exception e){
					this.m_parent.finish(HAPServiceData.createFailureData(e, ""));
				}
			}
			else{
				this.m_parent.finish(resourceTaskResult);
			}
		}
	}

	@Override
	public String getTaskType() {   return this.m_taskInfo.getTaskType();  }
}
