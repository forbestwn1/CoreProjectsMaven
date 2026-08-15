package com.nosliw.core.application.entity.taskinterface;

import org.json.JSONObject;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.resource.HAPPluginResourceManager;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;


public class HAPPluginResourceManagerTaskInterface implements HAPPluginResourceManager{

	private HAPServiceParseEntity m_entityParseService;
	
	private HAPTaskInterfaceConfigure m_taskInterfaceConfigure;
	
	public HAPPluginResourceManagerTaskInterface(HAPServiceParseEntity entityParseService, HAPTaskInterfaceConfigure taskInterfaceConfigure) {
		this.m_entityParseService = entityParseService;
		this.m_taskInterfaceConfigure = taskInterfaceConfigure;	}
	
	@Override
	public HAPResourceDataOrWrapper getResourceData(HAPResourceIdSimple simpleResourceId, HAPRuntimeInfo runtimeInfo) {
		HAPResourceDataTaskInterface out = new HAPResourceDataTaskInterface();
		
		String content = HAPUtilityTaskInterface.readTaskInterfaceContent(m_taskInterfaceConfigure, simpleResourceId.getId()); 

		JSONObject jsonObj = new JSONObject(content);

		//entity info
		JSONObject infoJsonObj = jsonObj.optJSONObject(HAPResourceDataTaskInterface.INFO);
		if(infoJsonObj!=null) {
			HAPEntityInfo entityInfo = new HAPEntityInfoImp();
			entityInfo.buildObject(infoJsonObj, HAPSerializationFormat.JSON);
			out.setEntityInfo(entityInfo);
		}
		
		//interface
		JSONObject serviceInterfaceJsonObj = jsonObj.getJSONObject(HAPResourceDataTaskInterface.INTERFACE);
		HAPInteractiveTask taskInterface = HAPInteractiveTask.parse(serviceInterfaceJsonObj, this.m_entityParseService); 
        out.setTaskInterface(taskInterface);
        
        return out;
	}

}
