package com.nosliw.core.data;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
@Component
public class HAPGatewayDataType extends HAPGatewayImp{

	@HAPAttribute
	final public static String COMMAND_GETRELATEDOPERATION = "getRelatedOperations";
	@HAPAttribute
	final public static String COMMAND_GETRELATEDOPERATION_DATATYPE_BASE = "dataTypeBase";
	@HAPAttribute
	final public static String COMMAND_GETRELATEDOPERATION_DATATYPE_RESULT = "dataTypeResult";
	
	@Autowired
	private HAPDataTypeHelper m_dataTypeHelper;
	
	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_DATATYPE;   }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		switch(command) {
		case COMMAND_GETRELATEDOPERATION:
			String baseDataType = (String)parms.opt(COMMAND_GETRELATEDOPERATION_DATATYPE_BASE);
			String resultDataType = (String)parms.opt(COMMAND_GETRELATEDOPERATION_DATATYPE_RESULT);
			List<HAPDataTypeOperation> out = this.m_dataTypeHelper.getDataTypeOperations(baseDataType==null?null:new HAPDataTypeId(baseDataType), resultDataType==null?null:new HAPDataTypeId(resultDataType));
			return this.createSuccessWithObject(out);
		}
		return null;
	}

}
