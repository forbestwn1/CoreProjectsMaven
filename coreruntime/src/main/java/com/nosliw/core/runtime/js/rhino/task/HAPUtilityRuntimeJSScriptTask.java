package com.nosliw.core.runtime.js.rhino.task;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.common.serialization.HAPJsonTypeAsItIs;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPInfoRuntimeTaskTaskScriptExpressionConstantGroup;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPInfoScriptFunction;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPUtilityScriptForExecuteJSScript;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPOperationParm;
import com.nosliw.core.data.expression.HAPInfoRuntimeTaskExecuteDataExpresion;
import com.nosliw.core.resource.infrastructure.HAPTaskRuntimeLoadResources;
import com.nosliw.core.runtime.execute.HAPTaskRuntime;
import com.nosliw.core.runtime.js.rhino.HAPExecutorRuntimeImpRhino;
import com.nosliw.core.runtime.js.rhino.HAPGatewayRhinoTaskResponse;

public class HAPUtilityRuntimeJSScriptTask {

	public static HAPJSScriptInfo buildRequestScriptForExecuteDataExpression(HAPInfoRuntimeTaskExecuteDataExpresion dataExpressionTaskInfo, String taskId, HAPExecutorRuntimeImpRhino runtime) {
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("operand", dataExpressionTaskInfo.getDataExpression().getOperand().toStringValue(HAPSerializationFormat.JAVASCRIPT));
		templateParms.put("variableDatas", HAPManagerSerialize.getInstance().toStringValue(dataExpressionTaskInfo.getVariableDatas(), HAPSerializationFormat.JSON));
		templateParms.put("constantDatas", HAPManagerSerialize.getInstance().toStringValue(dataExpressionTaskInfo.getConstantDatas(), HAPSerializationFormat.JSON));
		
		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "ExecuteDataExpression.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}
	
	public static HAPJSScriptInfo buildRequestScriptForLoadResourceTask(HAPTaskRuntimeLoadResources loadResourcesTask, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();

		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, loadResourcesTask.getTaskId(), runtime);

		templateParms.put("resourceInfos", HAPUtilityJson.formatJson(HAPUtilityJson.buildJson(loadResourcesTask.getResourcesInfo(), HAPSerializationFormat.JSON)));
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "LoadResources.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, loadResourcesTask.getTaskId());
		return out;
	}
	

	public static HAPJSScriptInfo buildTaskRequestScriptForExecuteExpressionScriptConstant(HAPInfoRuntimeTaskTaskScriptExpressionConstantGroup taskInfo, String taskId, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("taskInfo", taskInfo.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		
		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "ScriptExecuteScriptExpressionConstantGroup.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}
	
	public static HAPJSScriptInfo buildTaskRequestScriptForExecuteTaskGroupItemResource(String resourceType, String resourceId, String itemId, String taskId, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("resourceType", resourceType);
		templateParms.put("resourceId", resourceId);
		templateParms.put("itemId", itemId);
		
		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "ScriptExecuteResourceTaskGroupItem.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}
	
	public static HAPJSScriptInfo buildTaskRequestScriptForExecuteTaskResource(String resourceType, String resourceId, String taskId, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("resourceType", resourceType);
		templateParms.put("resourceId", resourceId);
		
		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "ScriptExecuteResourceTask.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}


	public static HAPJSScriptInfo buildTaskRequestScriptForExecuteTaskEntity(HAPExecutableBundle bundle, HAPPath mainEntityPath, String taskId, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("bundleDefinition", bundle.toResourceData(runtime.getRuntimeInfo()).toString());
		if(mainEntityPath==null||mainEntityPath.isEmpty()) {
			templateParms.put("mainEntityPath", "");
		} else {
			templateParms.put("mainEntityPath", mainEntityPath.getPath());
		}
		
		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "ScriptExecuteEntityTask.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}

	//build script for execute script expression task 
	public static HAPJSScriptInfo buildRequestScriptForExecuteScriptTask(HAPInfoRuntimeTaskScriptExpressionGroup taskInfo, HAPTaskRuntime task, HAPRuntimeImpRhino runtime){
		Map<String, Object> variableValue = taskInfo.getVariablesValue();

		Map<String, String> templateParms = new LinkedHashMap<String, String>();

		templateParms.put("variables", HAPUtilityJson.formatJson(HAPUtilityJson.buildJson(variableValue==null?new LinkedHashMap<String, HAPData>() : variableValue, HAPSerializationFormat.JSON)));

		//build javascript function to execute the script
		HAPInfoScriptFunction scriptFunctionInfo = taskInfo.getScriptFunction();
		templateParms.put("functionScript", scriptFunctionInfo.getMainScript().getScript());

		//functions
		String functionParmValue = "{}";
		List<HAPJSScriptInfo> childrenFun = scriptFunctionInfo.getChildren();
		if(!childrenFun.isEmpty()) {
			Map<String, String> funScriptMap = new LinkedHashMap<String, String>();
			Map<String, Class<?>> funScriptTypeMap = new LinkedHashMap<String, Class<?>>();
			for(HAPJSScriptInfo childFun : childrenFun) {
				funScriptMap.put(childFun.getName(), childFun.getScript());
				funScriptTypeMap.put(childFun.getName(), HAPJsonTypeAsItIs.class);
			}
			functionParmValue = HAPUtilityJson.buildMapJson(funScriptMap, funScriptTypeMap);
		}
		templateParms.put("functions", functionParmValue);
		
		templateParms.put("successCommand", HAPGatewayRhinoTaskResponse.COMMAND_SUCCESS);
		templateParms.put("errorCommand", HAPGatewayRhinoTaskResponse.COMMAND_ERROR);
		templateParms.put("exceptionCommand", HAPGatewayRhinoTaskResponse.COMMAND_EXCEPTION);
		
		templateParms.put("expressions", HAPUtilityJson.formatJson(HAPUtilityJson.buildJson(taskInfo.getExpressionItems(), HAPSerializationFormat.JSON)));
		templateParms.put("taskId", task.getTaskId());
		templateParms.put("constants", HAPUtilityJson.buildJson(taskInfo.getConstantsValue(), HAPSerializationFormat.JSON));

		templateParms.put("gatewayId", runtime.getTaskResponseGatewayName());
		templateParms.put("parmTaskId", HAPGatewayRhinoTaskResponse.PARM_TASKID);
		templateParms.put("parmResponseData", HAPGatewayRhinoTaskResponse.PARM_RESPONSEDATA);

		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityScriptForExecuteJSScript.class, "ExecuteScriptRequest.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, task.getTaskId());
		return out;
	}

	public static HAPJSScriptInfo buildRequestScriptForExecuteDataConvertTask(HAPTaskRuntimeExecuteConverterRhino executeConverterTask, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("data", HAPUtilityJson.formatJson(executeConverterTask.getTaskInfo().getData().toStringValue(HAPSerializationFormat.JSON)));
		templateParms.put("matchers", HAPUtilityJson.formatJson(executeConverterTask.getTaskInfo().getMatchers().toStringValue(HAPSerializationFormat.JSON)));

		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, executeConverterTask.getTaskId(), runtime);

		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "ExecuteDataConvertScript.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, executeConverterTask.getTaskId());
		return out;
	}
	
	public static HAPJSScriptInfo buildRequestScriptForExecuteDataOperationTask(HAPTaskRuntimeExecuteDataOperationRhino executeDataOperationTask, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("dataTypeId", executeDataOperationTask.getTaskInfo().getDataTypeId().toStringValue(HAPSerializationFormat.LITERATE));
		templateParms.put("operation", executeDataOperationTask.getTaskInfo().getOperation());
		templateParms.put("parmsArray", HAPUtilityJson.formatJson(HAPUtilityJson.buildJson(executeDataOperationTask.getTaskInfo().getParms()==null?new ArrayList<HAPOperationParm>() : executeDataOperationTask.getTaskInfo().getParms(), HAPSerializationFormat.JSON)));

		HAPUtilityRuntimeJSScript.buildCommonTemplateParms(templateParms, executeDataOperationTask.getTaskId(), runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScriptTask.class, "ExecuteDataOperationScript.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, executeDataOperationTask.getTaskId());
		return out;
	}

}
