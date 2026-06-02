package com.nosliw.core.application.entity.brickcriteria.facade.task;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.entity.brickcriteria.HAPRestrainBrick;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPRestrainBrickTypeFacadeTaskInterface extends HAPRestrainBrick{

	public final static String INTERFACE = "interface"; 
	
	private HAPInteractiveTask m_interactiveTaskInterface;
	
	public HAPRestrainBrickTypeFacadeTaskInterface() {
		super(HAPConstantShared.BRICKTYPECRITERIA_RESTRAIN_TASKINTERFACE);
	}
	
	public HAPInteractiveTask getTaskInteractiveInterface() {	return this.m_interactiveTaskInterface;	}
	public void setTaskInteractiveInterface(HAPInteractiveTask taskInterface) {	this.m_interactiveTaskInterface = taskInterface;	}

	public static HAPRestrainBrickTypeFacadeTaskInterface parse(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		HAPRestrainBrickTypeFacadeTaskInterface out = new HAPRestrainBrickTypeFacadeTaskInterface();
		out.setTaskInteractiveInterface(HAPInteractiveTask.parse(jsonObj.getJSONObject(INTERFACE), entityParseService));
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(INTERFACE, HAPManagerSerialize.getInstance().toStringValue(m_interactiveTaskInterface, HAPSerializationFormat.JSON));
	}
}
