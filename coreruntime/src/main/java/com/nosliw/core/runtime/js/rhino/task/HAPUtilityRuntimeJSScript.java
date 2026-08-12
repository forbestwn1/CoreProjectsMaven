package com.nosliw.core.runtime.js.rhino.task;

import java.io.InputStream;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.runtime.js.rhino.HAPExecutorRuntimeImpRhino;
import com.nosliw.core.runtime.js.rhino.HAPGatewayRhinoTaskResponse;

public class HAPUtilityRuntimeJSScript {

	public static void buildCommonTemplateParms(Map<String, String> templateParms, String taskId, HAPExecutorRuntimeImpRhino runtime) {
		templateParms.put("successCommand", HAPGatewayRhinoTaskResponse.COMMAND_SUCCESS);
		templateParms.put("errorCommand", HAPGatewayRhinoTaskResponse.COMMAND_ERROR);
		templateParms.put("exceptionCommand", HAPGatewayRhinoTaskResponse.COMMAND_EXCEPTION);
		
		templateParms.put("gatewayId", HAPConstantShared.GATEWAY_RHINOTASKRESPONSE);
		templateParms.put("parmTaskId", HAPGatewayRhinoTaskResponse.PARM_TASKID);
		templateParms.put("taskId", taskId);
		templateParms.put("parmResponseData", HAPGatewayRhinoTaskResponse.PARM_RESPONSEDATA);

		InputStream errorProcessTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPUtilityRuntimeJSScript.class, "PatchErrorProcessing.temp");
		String script = HAPStringTemplateUtil.getStringValue(errorProcessTemplateStream, templateParms);
		templateParms.put("errorProcess", script);
	}

}
