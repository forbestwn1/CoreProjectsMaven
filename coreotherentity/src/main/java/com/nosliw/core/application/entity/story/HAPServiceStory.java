package com.nosliw.core.application.entity.story;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneProviderRequest;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneResponse;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPServiceStory {

	@Autowired
	private HAPStoryServiceConfigure m_storyConfigure;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;

	@Autowired
	private RestTemplate m_restTemplate;
	
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		String responsStr = this.m_restTemplate.getForObject(m_storyConfigure.geturl()+"/bundle/"+brickId.getBrickTypeId().getBrickType()+"/" + brickId.getBrickTypeId().getVersion() + "/" + brickId.getId(), String.class);
		
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		
		return (HAPBundleForBrick)this.m_entityParseService.parseEntityJSONExplicit((JSONObject)serviceData.getData(), HAPBundleForBrick.class.getName());
	}	

	public HAPServiceData buildStandAlone(List<HAPManualStandaloneProviderRequest> requests) {
		String responsStr = this.m_restTemplate.postForObject(m_storyConfigure.geturl()+"standalone", HAPManagerSerialize.getInstance().toStringValue(requests, HAPSerializationFormat.JSON) ,String.class);
		
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		
		List<HAPManualStandaloneResponse> out = new ArrayList<HAPManualStandaloneResponse>();
		JSONArray responseJsonArray = (JSONArray)serviceData.getData();
		for(int i=0; i<responseJsonArray.length(); i++) {
			out.add((HAPManualStandaloneResponse)m_entityParseService.parseEntityJSONExplicit(responseJsonArray.getJSONObject(i), HAPManualStandaloneResponse.class.getName()));
		}
		
		return HAPServiceData.createSuccessData(out);
	}	

}
