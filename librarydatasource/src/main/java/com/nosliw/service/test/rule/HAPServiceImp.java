package com.nosliw.service.test.rule;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.core.application.common.datasource.HAPExecutableService;
import com.nosliw.core.application.common.datasource.HAPProviderService;
import com.nosliw.core.application.common.datasource.HAPUtilityService;
import com.nosliw.core.application.common.interactive.HAPResultInteractiveTask;
import com.nosliw.core.data.HAPData;

public class HAPServiceImp implements HAPExecutableService, HAPProviderService{

	@Override
	public HAPResultInteractiveTask execute(Map<String, HAPData> parms) {
		Map<String, HAPData> output = new LinkedHashMap<String, HAPData>();
		HAPData parm1 = parms.get("serviceParm1");

		if(parm1!=null) {
			if("error".equals(parm1.getValue())){
				return HAPUtilityService.generateFailResult("the value should not be error!!!!!!");
			}
		}
		
		output.put("outputStringInService1", parm1);
		return HAPUtilityService.generateSuccessResult(output);
	}

}
