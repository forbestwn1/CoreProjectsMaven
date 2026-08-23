package com.nosliw.core.application.common.gateway;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.core.gateway.HAPGatewayOutput;
import com.nosliw.core.gateway.HAPServiceInfo;

public class HAPUtilityGateway {

	public static HAPServiceData executeGatewaySingle(String url, String gatewayName, String command, Map<String, Object> parmsValue, RestTemplate restTemplate) {
		JSONObject parmJson = new JSONObject(HAPManagerSerialize.getInstance().toStringValue(parmsValue, HAPSerializationFormat.JSON));
		HAPServiceInfo serviceInfo = new HAPServiceInfo(HAPUtilityNamingConversion.cascadeLevel1(gatewayName, command), parmJson) ;
		String responsStr = restTemplate.postForObject(url+"gatewaysingle", serviceInfo.toStringValue(HAPSerializationFormat.JSON), String.class);
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		
		JSONObject gatewayOutputJsonObj = (JSONObject)serviceData.getData();
		HAPGatewayOutput gatewayOutput = new HAPGatewayOutput();
		gatewayOutput.buildObject(gatewayOutputJsonObj, HAPSerializationFormat.JSON);
		
		return HAPServiceData.createSuccessData(gatewayOutput);
	}
	
}
