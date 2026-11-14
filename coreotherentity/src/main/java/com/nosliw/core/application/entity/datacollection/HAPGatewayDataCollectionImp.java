package com.nosliw.core.application.entity.datacollection;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.gateway.types.datacollection.HAPGatewayDataCollection;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
@Component
public class HAPGatewayDataCollectionImp extends HAPGatewayImp implements HAPGatewayDataCollection{

	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_DATACOLLECTION;  }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		
		
		return null;
	}

}
