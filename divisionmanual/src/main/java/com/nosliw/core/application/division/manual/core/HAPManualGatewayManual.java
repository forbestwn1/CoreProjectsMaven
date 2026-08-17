package com.nosliw.core.application.division.manual.core;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.event.manual.HAPManualContentProviderText;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPManualGatewayManual extends HAPGatewayImp{
	
	@HAPAttribute
	static public final String COMMAND_COMPILE = "compile";
	@HAPAttribute
	static public final String PARMS_CONTENT = "content";
	
	@Autowired
	private HAPManualManagerBrick m_manualManager;
	
	@Autowired
	private HAPServiceParseEntity m_parseService;
	
	public HAPManualGatewayManual() {
	}
	
	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_MANUAL;  }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		if(COMMAND_COMPILE.equals(command)) {
			HAPManualContentProviderText contentProvider = (HAPManualContentProviderText)m_parseService.parseEntityJSONExplicit(parms.getJSONObject(PARMS_CONTENT), HAPManualContentProviderText.class.getName());
			HAPBundleForBrick bundle = this.m_manualManager.buildBundle(contentProvider, runtimeInfo);
			return this.createSuccessWithObject(bundle);
		}
		return null;
	}

}
