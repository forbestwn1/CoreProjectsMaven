package com.nosliw.core.service.staticresource;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public class HAPServiceStaticResource {

	private String m_staticServerUrl;
	
	private RestTemplate m_restTemplate;
	
	private HAPServiceParseEntity m_parseServices;
	
	public HAPServiceStaticResource(String staticServerUrl, RestTemplate restTemplate, HAPServiceParseEntity parseServices) {
		this.m_restTemplate = restTemplate;
		this.m_staticServerUrl = staticServerUrl;
		this.m_parseServices = parseServices;
	}
	
	public HAPServiceData getStatic(HAPStaticRequest staticRequest) {
		String responsStr = this.m_restTemplate.postForObject(m_staticServerUrl+"fetch", staticRequest.toStringValue(HAPSerializationFormat.JSON), String.class);
        return this.processResponse(responsStr, this.m_parseServices);
	}
	
	public HAPServiceData upload(String content, String domain, String name) {
		
		StringBuffer url = new StringBuffer(this.m_staticServerUrl+"upload");
		StringBuffer parms = new StringBuffer();
		int num = 0;
		if(domain!=null) {
			parms.append("domain=" + domain);
			num++;
		}
		if(name!=null) {
			if(num>0) {
				parms.append("&");
			}
			parms.append("name=" + name);
			num++;
		}
		if(num>0) {
			url.append("?");
			url.append(parms);
		}
		
		String responsStr = this.m_restTemplate.postForObject(url.toString(), content, String.class);
        return this.processResponse(responsStr, this.m_parseServices);
	}

	private HAPServiceData processResponse(String responsStr, HAPServiceParseEntity parseServices) {
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		HAPStaticResponse staticResponse = HAPStaticResponse.parse((JSONObject)serviceData.getData(), parseServices);
		return HAPServiceData.createSuccessData(staticResponse);
	}
	
}
