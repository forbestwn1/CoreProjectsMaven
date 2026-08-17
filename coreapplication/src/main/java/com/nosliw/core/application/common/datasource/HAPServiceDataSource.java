package com.nosliw.core.application.common.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.interactive.HAPResultInteractiveTask;
import com.nosliw.core.data.HAPData;

public class HAPServiceDataSource {

	private HAPServiceParseEntity m_entityParseService;
	
	private String m_url;

	public HAPServiceDataSource(String url, HAPServiceParseEntity entityParseService) {
		this.m_entityParseService = entityParseService;
		this.m_url = url;
	} 
	
	public HAPServiceProfile getServiceProfile(String id){
		RestTemplate restTemplate = new RestTemplate();
		String responsStr = restTemplate.getForObject(this.m_url+"instance/profile/"+id, String.class);
		
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		
		HAPServiceProfile out = HAPServiceProfile.parse((JSONObject)serviceData.getData(), this.m_entityParseService);
		return out;
	}

	
	
	public List<HAPServiceProfile> queryDefinition(HAPQueryServiceDefinition query){
		RestTemplate restTemplate = new RestTemplate();
		String responsStr = restTemplate.postForObject(this.m_url+"instance/profile/search", query.toStringValue(HAPSerializationFormat.JSON), String.class);

		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);

		List<HAPServiceProfile> out = new ArrayList<HAPServiceProfile>();
		JSONArray outJsonArray = (JSONArray)serviceData.getData();
		for(int i=0; i<outJsonArray.length(); i++) {
			out.add(HAPServiceProfile.parse(outJsonArray.getJSONObject(i), m_entityParseService));
		}
	
		return out;
	}	
	
	public HAPServiceInterface getServiceInterface(String id) {
		return null;
	}

	public HAPResultInteractiveTask execute(HAPQueryService serviceQuery, Map<String, HAPData> parms) {
		RestTemplate restTemplate = new RestTemplate();
		
		HAPAIPRequestExectueDataSource request = new HAPAIPRequestExectueDataSource(serviceQuery, parms);
		String responsStr = restTemplate.postForObject(this.m_url+"instance/exectue", request.toStringValue(HAPSerializationFormat.JSON), String.class);
		
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		
		HAPResultInteractiveTask out = new HAPResultInteractiveTask();
		out.buildObject(serviceData.getData(), HAPSerializationFormat.JSON);
		
		return out;
	}
	
}

