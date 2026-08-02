package com.nosliw.core.application.common.command;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPCommandDefinition extends HAPEntityInfoImp{

	@HAPAttribute
	public final static String INTERFACE = "interface"; 
	
	//command interface
	private HAPInteractiveTask m_taskInterface;
	

	public HAPInteractiveTask getTaskInterface() {     return this.m_taskInterface;      }
	public void setTaskInterface(HAPInteractiveTask taskInterface) {     }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_taskInterface!=null) {
			jsonMap.put(INTERFACE, this.getTaskInterface().toStringValue(HAPSerializationFormat.JSON));
		}
	}
	
	
	public static HAPCommandDefinition parseCommandDefinition(Object obj, HAPServiceParseEntity entityParseService) {
		if(obj==null) {
			return null;
		}
		
		HAPCommandDefinition out = new HAPCommandDefinition();
		
		if(obj instanceof JSONObject) {
			JSONObject jsonObj = (JSONObject)obj;
			out.buildEntityInfoByJson(jsonObj);
			JSONObject dfObjJson = jsonObj.optJSONObject(HAPCommandDefinition.INTERFACE);
			if(dfObjJson!=null) {
				out.setTaskInterface(HAPInteractiveTask.parse(dfObjJson, entityParseService));
			}
		}
		
        return out;
	}

}
