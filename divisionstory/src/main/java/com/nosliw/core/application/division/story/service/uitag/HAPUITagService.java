package com.nosliw.core.application.division.story.service.uitag;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.gateway.HAPUtilityGateway;
import com.nosliw.core.application.common.uitag.HAPGatewayUITag;
import com.nosliw.core.application.common.uitag.HAPUITagInfo;
import com.nosliw.core.application.common.uitag.HAPUITageQueryData;
import com.nosliw.core.application.division.story.service.HAPGatewayServiceConfigure;

@Component
public class HAPUITagService {

	@Autowired
	private HAPGatewayServiceConfigure gatewayServiceConfigure;
	
	@Autowired
	private RestTemplate m_resteTemplate;
	
	public HAPUITagInfo getDefaultUITagData(HAPUITageQueryData uiTagQuery) {
		
		Map<String, Object> parmValue = new LinkedHashMap<String, Object>();
		parmValue.put(HAPGatewayUITag.COMMAND_GETDEFAULTTAG_CRITERIA, uiTagQuery);
		
		HAPServiceData serviceData = HAPUtilityGateway.executeGatewaySingle(
				gatewayServiceConfigure.getUrlSingle(), 
				HAPConstantShared.GATEWAY_UITAG, 
				HAPGatewayUITag.COMMAND_GETDEFAULTTAG, 
				parmValue, 
				m_resteTemplate);
		
		HAPUITagInfo out = new HAPUITagInfo();
		out.buildObject(serviceData.getData(), HAPSerializationFormat.JSON);
		return out;
	}
	
}
