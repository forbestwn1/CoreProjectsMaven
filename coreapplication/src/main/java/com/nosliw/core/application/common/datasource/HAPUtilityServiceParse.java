package com.nosliw.core.application.common.datasource;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.imp.basic.HAPBasicBlockInteractiveInterfaceTask;
import com.nosliw.core.application.brick.spec.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveTask;

public class HAPUtilityServiceParse {

	public static HAPBlockInteractiveInterfaceTask parseTaskInterfaceInterfaceBlock(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		JSONObject serviceInterfaceJsonObj = jsonObj.optJSONObject(HAPWithBlockInteractiveTask.TASKINTERFACE);
		if(serviceInterfaceJsonObj==null) {
			serviceInterfaceJsonObj = jsonObj;
		}
		HAPBasicBlockInteractiveInterfaceTask interfaceBlock = new HAPBasicBlockInteractiveInterfaceTask();
		HAPInteractiveTask taskInterface = HAPInteractiveTask.parse(serviceInterfaceJsonObj, entityParseService); 
		interfaceBlock.setValue(taskInterface);
		return interfaceBlock;
	}
	
}
