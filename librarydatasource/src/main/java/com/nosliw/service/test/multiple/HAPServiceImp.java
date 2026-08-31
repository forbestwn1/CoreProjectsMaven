package com.nosliw.service.test.multiple;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;

import com.nosliw.core.application.common.datasource.HAPExecutableService;
import com.nosliw.core.application.common.datasource.HAPProviderService;
import com.nosliw.core.application.common.datasource.HAPUtilityService;
import com.nosliw.core.application.common.interactive.HAPResultInteractiveTask;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPDataWrapper;

public class HAPServiceImp implements HAPExecutableService, HAPProviderService{

	@Override
	public HAPResultInteractiveTask execute(Map<String, HAPData> parms) {
		Map<String, HAPData> output = new LinkedHashMap<String, HAPData>();
		HAPData parm1 = parms.get("serviceParmString");
		
		HAPData result = null;
		Object parm1Value = parm1.getValue();
		if(parm1Value instanceof JSONArray) {
			JSONArray valueJsonArray = (JSONArray)parm1Value;
			for(int i=0; i<valueJsonArray.length(); i++) {
				result = new HAPDataWrapper(parm1.getDataTypeId(), valueJsonArray.get(i));
			}
		}
		else {
			result = parm1;
		}
		
		output.put("outputInService1", result);
		return HAPUtilityService.generateSuccessResult(output);
	}

}
