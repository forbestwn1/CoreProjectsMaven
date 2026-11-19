package com.nosliw.core.gateway.types.manual;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.manual.HAPManualContentProviderText;
import com.nosliw.core.application.entity.manual.HAPServiceManual;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
@Component
public class HAPManualGatewayCompile extends HAPGatewayImp{

	@HAPAttribute
	static public final String COMMAND_COMPILE = "compile";
	@HAPAttribute
	static public final String PARMS_CONTENT = "content";
	
	@Autowired
	private HAPServiceManual m_manualService;
	
	@Autowired
	private HAPServiceParseEntity m_parseService;
	
	public HAPManualGatewayCompile() {
	}
	
	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_MANUAL_COMPILE;  }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		if(COMMAND_COMPILE.equals(command)) {
			HAPManualContentProviderText contentProvider = (HAPManualContentProviderText)m_parseService.parseEntityJSONExplicit(parms.getJSONObject(PARMS_CONTENT), HAPManualContentProviderText.ENTITYNAMEFORSERIALIZE);
			HAPBundleForBrick bundle = this.m_manualService.buildBundle(contentProvider, runtimeInfo);
			return this.createSuccessWithObject(bundle);
		}
		return null;
	}
}
