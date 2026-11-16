package com.nosliw.core.application.entity.datacollection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.gateway.types.datacollection.HAPGatewayDataCollection;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
@Component
public class HAPGatewayDataCollectionImp extends HAPGatewayImp implements HAPGatewayDataCollection{

	@Autowired
	private ApplicationHitRepository m_appHitRepository;
	
	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_DATACOLLECTION;  }

	@Override
	@Transactional
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		if(command.equals(HAPGatewayDataCollection.COMMAND_REQUEST_APPLICATION)) {
			ApplicationHit appHit = new ApplicationHit();
			appHit.setApp((String)parms.opt(HAPGatewayDataCollection.COMMAND_REQUEST_APPLICATION_APPLICATION));
			appHit.setTime((String)parms.opt(HAPGatewayDataCollection.COMMAND_REQUEST_APPLICATION_TIME));
			appHit.setParm((String)parms.opt(HAPGatewayDataCollection.COMMAND_REQUEST_APPLICATION_RESOURCEID));
			m_appHitRepository.save(appHit);
		}
		
		return HAPServiceData.createSuccessData();
	}

}
