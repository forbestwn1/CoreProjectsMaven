package com.nosliw.core.service;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.staticc.HAPStaticRequest;
import com.nosliw.common.staticc.HAPStaticResponse;

@Component
public class HAPServiceStaticResource {

	public HAPServiceData getStatic(HAPStaticRequest staticRequest) {
		RestTemplate restTemplate = new RestTemplate();
		String responsStr = restTemplate.postForObject("http://localhost:8081/nosliw/static", staticRequest.toStringValue(HAPSerializationFormat.JSON), String.class);
        return this.processResponse(responsStr);
	}
	
	public HAPServiceData upload(String content, String domain, String name) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		StringBuffer url = new StringBuffer("http://localhost:8081/nosliw/upload");
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
		
		String responsStr = restTemplate.postForObject(url.toString(), content, String.class);
        return this.processResponse(responsStr);
	}

	private HAPServiceData processResponse(String responsStr) {
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		
		HAPStaticResponse staticResponse = new HAPStaticResponse();
		staticResponse.buildObject(serviceData.getData(), HAPSerializationFormat.JSON);
		return HAPServiceData.createSuccessData(staticResponse);
	}
	
}
