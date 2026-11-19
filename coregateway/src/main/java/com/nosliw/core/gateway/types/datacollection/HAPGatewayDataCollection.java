package com.nosliw.core.gateway.types.datacollection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.datacollection.ApplicationHit;
import com.nosliw.core.datacollection.ApplicationHitRepository;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
@Component
public class HAPGatewayDataCollection extends HAPGatewayImp{

	@HAPAttribute
	final public static String COMMAND_REQUEST_ERROR = "error";

	@HAPAttribute
	final public static String COMMAND_REQUEST_ERROR_TIME = "time";

	@HAPAttribute
	final public static String COMMAND_REQUEST_ERROR_CONTENT = "content";

	@HAPAttribute
	final public static String COMMAND_REQUEST_APPLICATION = "application";

	@HAPAttribute
	final public static String COMMAND_REQUEST_APPLICATION_APPLICATION = "application";

	@HAPAttribute
	final public static String COMMAND_REQUEST_APPLICATION_TIME = "time";

	@HAPAttribute
	final public static String COMMAND_REQUEST_APPLICATION_RESOURCEID = "resourceid";

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
