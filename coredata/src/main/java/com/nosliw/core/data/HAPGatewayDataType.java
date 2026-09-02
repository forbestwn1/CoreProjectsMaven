package com.nosliw.core.data;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPGatewayDataType extends HAPGatewayImp{

	@HAPAttribute
	final public static String COMMAND_GETRELATEDOPERATIONS = "getRelatedOperations";
	
	
	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_DATATYPE;   }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
