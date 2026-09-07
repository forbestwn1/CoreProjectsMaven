package com.nosliw.service.real.toronto.dropin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.common.datasource.HAPExecutableService;
import com.nosliw.core.application.common.datasource.HAPProviderService;
import com.nosliw.core.application.common.datasource.HAPUtilityService;
import com.nosliw.core.application.common.interactive.HAPResultInteractiveTask;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;

public class HAPServiceImp implements HAPExecutableService, HAPProviderService{

	@Override
	public HAPResultInteractiveTask execute(Map<String, HAPData> parms) {
		Map<String, HAPData> output = new LinkedHashMap<String, HAPData>();

		String dataItemStr = HAPUtilityFile.readFile(getClass(), "dataitem.json");
		
		List<String> arrayItem = new ArrayList<String>();
		for(int i=0; i<20; i++) {
			arrayItem.add(dataItemStr);
		}
		
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("dataTypeId", "test.array;1.0.0");
		dataMap.put("value", HAPUtilityJson.buildArrayJson(arrayItem.toArray(new String[0])));

		output.put("output", HAPUtilityData.buildDataWrapperFromJson(new JSONObject(HAPUtilityJson.buildMapJson(dataMap))));
		return HAPUtilityService.generateSuccessResult(output);
	}

}
