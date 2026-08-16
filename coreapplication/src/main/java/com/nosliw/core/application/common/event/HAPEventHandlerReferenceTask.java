package com.nosliw.core.application.common.event;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPPackageBrickInBundle;

public class HAPEventHandlerReferenceTask extends HAPEventHandlerReference{

	@HAPAttribute
	public static final String TASKBRICKPACKAGE = "taskBrickPackage";

	private HAPPackageBrickInBundle m_taskBrickPackage;
	
	public HAPEventHandlerReferenceTask() {}
	
	@Override
	public String getHandlerType() {   return HAPConstantShared.EVENT_HANDLERTYPE_TASK;   }

	public void setTaskBrickPackage(HAPPackageBrickInBundle taskBrickId) {	this.m_taskBrickPackage = taskBrickId;	}
	
	public HAPPackageBrickInBundle getTaskBrickPackage() {    return this.m_taskBrickPackage;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TASKBRICKPACKAGE, this.getTaskBrickPackage().toStringValue(HAPSerializationFormat.JSON));
	}
}

@Component
class HAPEventHandlerReferenceTask_parser extends HAPEventHandlerReference_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPEventHandlerReferenceTask out = new HAPEventHandlerReferenceTask();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject taskBrickJsonObj = jsonObj.optJSONObject(HAPEventHandlerReferenceTask.TASKBRICKPACKAGE);
		if(taskBrickJsonObj!=null) {
			HAPPackageBrickInBundle taskBrickId = new HAPPackageBrickInBundle();
			taskBrickId.buildObject(taskBrickJsonObj, HAPSerializationFormat.JSON);
			out.setTaskBrickPackage(taskBrickId);
		}
		
		return out;
	}

	@Override
	public String getSubName() {    return HAPConstantShared.EVENT_HANDLERTYPE_TASK;   }
	
}
