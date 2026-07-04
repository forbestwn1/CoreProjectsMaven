package com.nosliw.core.runtime.js.rhino.task;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.common.serialization.HAPJsonTypeAsItIs;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPInfoRuntimeTaskTaskScriptExpressionConstantGroup;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPInfoScriptFunction;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPUtilityScriptForExecuteJSScript;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.resource.infrastructure.HAPRuntimeTaskLoadResources;
import com.nosliw.core.runtime.execute.HAPTaskRuntime;
import com.nosliw.core.runtime.js.rhino.HAPExecutorRuntimeImpRhino;
import com.nosliw.core.runtime.js.rhino.HAPGatewayRhinoTaskResponse;

public class HAPUtilityRuntimeJSScript {

	public static HAPJSScriptInfo buildRequestScriptForLoadResourceTask(HAPRuntimeTaskLoadResources loadResourcesTask, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("successCommand", HAPGatewayRhinoTaskResponse.COMMAND_SUCCESS);
		templateParms.put("errorCommand", HAPGatewayRhinoTaskResponse.COMMAND_ERROR);
		templateParms.put("exceptionCommand", HAPGatewayRhinoTaskResponse.COMMAND_EXCEPTION);
		
		templateParms.put("gatewayId", HAPConstantShared.GATEWAY_RHINOTASKRESPONSE);
		templateParms.put("parmTaskId", HAPGatewayRhinoTaskResponse.PARM_TASKID);
		templateParms.put("taskId", loadResourcesTask.getTaskId());
		templateParms.put("parmResponseData", HAPGatewayRhinoTaskResponse.PARM_RESPONSEDATA);

		templateParms.put("resourceInfos", HAPUtilityJson.formatJson(HAPUtilityJson.buildJson(loadResourcesTask.getResourcesInfo(), HAPSerializationFormat.JSON)));
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScript.class, "LoadResources.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, loadResourcesTask.getTaskId());
		return out;
	}
	

	public static HAPJSScriptInfo buildTaskRequestScriptForExecuteExpressionScriptConstant(HAPInfoRuntimeTaskTaskScriptExpressionConstantGroup taskInfo, String taskId, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("taskInfo", taskInfo.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		
		buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScript.class, "ScriptExecuteScriptExpressionConstantGroup.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}
	
	public static HAPJSScriptInfo buildTaskRequestScriptForExecuteTaskGroupItemResource(String resourceType, String resourceId, String itemId, String taskId, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("resourceType", resourceType);
		templateParms.put("resourceId", resourceId);
		templateParms.put("itemId", itemId);
		
		buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScript.class, "ScriptExecuteResourceTaskGroupItem.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}
	
	public static HAPJSScriptInfo buildTaskRequestScriptForExecuteTaskResource(String resourceType, String resourceId, String taskId, HAPExecutorRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		templateParms.put("resourceType", resourceType);
		templateParms.put("resourceId", resourceId);
		
		buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScript.class, "ScriptExecuteResourceTask.temp");
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
		
		buildCommonTemplateParms(templateParms, taskId, runtime);
		
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScript.class, "ScriptExecuteEntityTask.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, taskId);
		return out;
	}

	private static void buildCommonTemplateParms(Map<String, String> templateParms, String taskId, HAPExecutorRuntimeImpRhino runtime) {
		templateParms.put("successCommand", HAPGatewayRhinoTaskResponse.COMMAND_SUCCESS);
		templateParms.put("errorCommand", HAPGatewayRhinoTaskResponse.COMMAND_ERROR);
		templateParms.put("exceptionCommand", HAPGatewayRhinoTaskResponse.COMMAND_EXCEPTION);
		
		templateParms.put("gatewayId", HAPConstantShared.GATEWAY_RHINOTASKRESPONSE);
		templateParms.put("parmTaskId", HAPGatewayRhinoTaskResponse.PARM_TASKID);
		templateParms.put("taskId", taskId);
		templateParms.put("parmResponseData", HAPGatewayRhinoTaskResponse.PARM_RESPONSEDATA);
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


}
