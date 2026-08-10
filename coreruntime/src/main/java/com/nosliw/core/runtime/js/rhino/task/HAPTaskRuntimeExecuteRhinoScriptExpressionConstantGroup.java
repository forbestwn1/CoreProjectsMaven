package com.nosliw.core.runtime.js.rhino.task;

import java.util.List;

import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPInfoRuntimeTaskTaskScriptExpressionConstantGroup;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.runtime.execute.HAPInfoRuntimeTask;
import com.nosliw.core.runtime.js.rhino.HAPExecutorRuntimeImpRhino;
import com.nosliw.core.runtime.js.rhino.HAPTaskRuntimeRhino;

public class HAPTaskRuntimeExecuteRhinoScriptExpressionConstantGroup extends HAPTaskRuntimeRhino{

	private HAPInfoRuntimeTaskTaskScriptExpressionConstantGroup m_scriptExpressionGroupInfo;
	
	public HAPTaskRuntimeExecuteRhinoScriptExpressionConstantGroup(HAPInfoRuntimeTask scriptExpressionGroupInfo) {
		super(HAPInfoRuntimeTask.RUNTIMETASK_TYPE_EXECUTESCRIPTEXPRESSIONCONSTANTGROUP);
		this.m_scriptExpressionGroupInfo = (HAPInfoRuntimeTaskTaskScriptExpressionConstantGroup)scriptExpressionGroupInfo;
	}

	@Override
	public Class getResultDataType() {
		return Object.class;
	}

	@Override
	protected List<HAPResourceDependency> getResourceDependency() {
		return null;
//		return this.m_taskInfo.getExpression().getResourceDependency(this.getRuntime().getRuntimeInfo(), this.getRuntimeEnv().getResourceManager());
	}

	@Override
	protected HAPJSScriptInfo buildRuntimeScript(HAPExecutorRuntimeImpRhino runtime) {
		HAPJSScriptInfo scriptInfo = HAPUtilityRuntimeJSScript.buildTaskRequestScriptForExecuteExpressionScriptConstant(m_scriptExpressionGroupInfo, getTaskId(), runtime);
		return scriptInfo;
	}
}
