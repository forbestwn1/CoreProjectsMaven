package com.nosliw.core.application.entity.taskinterface;

import java.io.File;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.brick.service.interfacee.HAPBlockServiceInterface;
import com.nosliw.core.application.entity.datasource.HAPIdServcieInterface;
import com.nosliw.core.application.entity.datasource.HAPUtilityServiceParse;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.system.HAPSystemFolderUtility;

@Component
public class HAPManagerServiceInterface{
	
	@Autowired
	HAPServiceParseEntity m_entityParseService;
	
	public HAPManagerServiceInterface() {
	}
	
	public HAPBlockServiceInterface getServiceInterface(HAPIdServcieInterface id) {
		HAPResourceDataTaskInterface out = new HAPResourceDataTaskInterface();
		
		String fileName = HAPSystemFolderUtility.getServiceInterfaceFolder() + id.getId() + ".json";
		String content = HAPUtilityFile.readFile(new File(fileName));

		JSONObject jsonObj = new JSONObject(content);

		//entity info
		out.buildEntityInfoByJson(jsonObj);
		
		//interface
		out.setTaskInteractiveInterface(HAPUtilityServiceParse.parseTaskInterfaceInterfaceBlock(jsonObj, m_entityParseService));
		
		return out;
	}
}
