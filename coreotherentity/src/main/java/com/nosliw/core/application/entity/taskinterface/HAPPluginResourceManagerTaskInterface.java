package com.nosliw.core.application.entity.taskinterface;

import java.io.File;

import org.json.JSONObject;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.resource.HAPPluginResourceManager;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.system.HAPSystemFolderUtility;


public class HAPPluginResourceManagerTaskInterface implements HAPPluginResourceManager{

	private HAPServiceParseEntity m_entityParseService;
	
	public HAPPluginResourceManagerTaskInterface(HAPServiceParseEntity entityParseService) {
		this.m_entityParseService = entityParseService;
	}
	
	@Override
	public HAPResourceDataOrWrapper getResourceData(HAPResourceIdSimple simpleResourceId, HAPRuntimeInfo runtimeInfo) {
		HAPResourceDataTaskInterface out = new HAPResourceDataTaskInterface();
		
		String fileName = HAPSystemFolderUtility.getServiceInterfaceFolder() + simpleResourceId.getId() + ".json";
		String content = HAPUtilityFile.readFile(new File(fileName));

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
