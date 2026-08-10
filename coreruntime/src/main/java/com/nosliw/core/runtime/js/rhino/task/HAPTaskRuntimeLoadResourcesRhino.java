package com.nosliw.core.runtime.js.rhino.task;

import java.util.List;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.core.resource.HAPResourceInfo;
import com.nosliw.core.resource.infrastructure.HAPTaskRuntimeLoadResources;
import com.nosliw.core.runtime.execute.HAPExecutorRuntime;
import com.nosliw.core.runtime.execute.HAPTaskRuntime;
import com.nosliw.core.runtime.js.rhino.HAPExecutorRuntimeImpRhino;

public class HAPTaskRuntimeLoadResourcesRhino extends HAPTaskRuntimeLoadResources{

	public HAPTaskRuntimeLoadResourcesRhino(List<HAPResourceInfo> resourcesInfo) {
		super(resourcesInfo);
	}

	@Override
	public HAPTaskRuntime execute(HAPExecutorRuntime runtime) {
		try{
			if(this.getResourcesInfo()==null || this.getResourcesInfo().isEmpty()){
				//if no resource required
				this.finish(HAPServiceData.createSuccessData());
			}
			else{
				HAPExecutorRuntimeImpRhino rhinoRuntime = (HAPExecutorRuntimeImpRhino)runtime;
				HAPJSScriptInfo scriptInfo = HAPUtilityRuntimeJSScript.buildRequestScriptForLoadResourceTask(this, rhinoRuntime);
				rhinoRuntime.loadTaskScript(scriptInfo, this.getTaskId());
			}
		}
		catch(Exception e){
			this.finish(HAPServiceData.createFailureData(e, ""));
		}
		return null;
	}
}
