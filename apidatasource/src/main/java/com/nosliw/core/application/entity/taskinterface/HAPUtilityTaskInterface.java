package com.nosliw.core.application.entity.taskinterface;

import org.json.JSONObject;

import com.nosliw.application.datasource.api.HAPConfigureDataSource;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.brick.spec.service.interfacee.HAPBlockServiceInterface;
import com.nosliw.core.application.entity.datasource.HAPBlockServiceInterfaceImp;

public class HAPUtilityTaskInterface {

	public static String readTaskInterfaceContent(HAPConfigureDataSource taskInterfaceConfigure, String id) {
		return HAPUtilityFileNio.readFile(HAPUtilityFileNio.buildPath(taskInterfaceConfigure.getPath(), id + ".json"));
	}

	public static HAPBlockServiceInterface buildTaskInterfaceBlock(HAPConfigureDataSource taskInterfaceConfigure, String id) {
		HAPBlockServiceInterfaceImp out = new HAPBlockServiceInterfaceImp(HAPConstantShared.BRICK_DIVISION_SERVICE);
		
		String content = readTaskInterfaceContent(taskInterfaceConfigure, id);
		JSONObject jsonObj = new JSONObject(content);

		//entity info
		out.buildEntityInfoByJson(jsonObj);
		
		//interface
		
		return out;
		
	}
	
	
}
