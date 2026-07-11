package com.nosliw.core.runtime.js.rhino.task;

import java.util.List;

import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.core.data.expression.HAPInfoRuntimeTaskExecuteDataExpresion;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.runtime.execute.HAPInfoRuntimeTask;
import com.nosliw.core.runtime.js.rhino.HAPExecutorRuntimeImpRhino;
import com.nosliw.core.runtime.js.rhino.HAPRuntimeTaskRhino;

public class HAPRuntimeTaskExecuteDataExpressionRhino extends HAPRuntimeTaskRhino{

	private HAPInfoRuntimeTaskExecuteDataExpresion m_dataExpressionTaskInfo;
	
	public HAPRuntimeTaskExecuteDataExpressionRhino(HAPInfoRuntimeTask taskInfo) {
		super(HAPInfoRuntimeTask.RUNTIMETASK_TYPE_EXECUTEDATAEXPRESSION);
		this.m_dataExpressionTaskInfo = (HAPInfoRuntimeTaskExecuteDataExpresion)taskInfo;
	}

	@Override
	protected List<HAPResourceDependency> getResourceDependency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HAPJSScriptInfo buildRuntimeScript(HAPExecutorRuntimeImpRhino runtime) {
		return HAPUtilityRuntimeJSScript.buildRequestScriptForExecuteDataExpression(m_dataExpressionTaskInfo, getTaskId(), runtime);
	}

}
