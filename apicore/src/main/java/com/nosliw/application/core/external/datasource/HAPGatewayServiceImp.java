package com.nosliw.application.core.external.datasource;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datasource.HAPQueryService;
import com.nosliw.core.application.common.datasource.HAPQueryServiceDefinition;
import com.nosliw.core.application.common.datasource.HAPServiceDataSource;
import com.nosliw.core.application.common.datasource.HAPServiceProfile;
import com.nosliw.core.application.common.interactive.HAPResultInteractiveTask;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.gateway.types.HAPGatewayService;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPGatewayServiceImp extends HAPGatewayImp implements HAPGatewayService{

	@Autowired
	private HAPServiceDataSource m_dataSourceService;

	public HAPGatewayServiceImp(){
	}
	
	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_SERVICE;  }
	
	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		HAPServiceData out = null;
		switch(command){
		case COMMAND_REQUEST:
		{
			HAPQueryService serviceQuery = new HAPQueryService();
			serviceQuery.buildObject(parms.optJSONObject(COMMAND_REQUEST_QUERY), HAPSerializationFormat.JSON);
			JSONObject parmsJson = parms.optJSONObject(COMMAND_REQUEST_PARMS);
			Map<String, HAPData> dataSourceParms = HAPUtilityData.buildDataWrapperMapFromJson(parmsJson);
			
			HAPResultInteractiveTask serviceResult = this.m_dataSourceService.execute(serviceQuery, dataSourceParms);
			out = this.createSuccessWithObject(serviceResult);
			break;
		}
		case COMMAND_SEARCHDEFINITION:
		{
			HAPQueryServiceDefinition defQuery = new HAPQueryServiceDefinition();
			defQuery.buildObject(parms.optJSONObject(COMMAND_SEARCHDEFINITION_QUERY), HAPSerializationFormat.JSON);
			List<HAPServiceProfile> serviceDefs = this.m_dataSourceService.queryDefinition(defQuery);
			out = this.createSuccessWithObject(serviceDefs);
			return out;
		}
		}
		return out;
	}

}
