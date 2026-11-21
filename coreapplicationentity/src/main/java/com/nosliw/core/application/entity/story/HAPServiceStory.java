package com.nosliw.core.application.entity.story;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.HAPIdBrick;
import com.nosliw.core.application.common.standalone.HAPStandaloneProviderRequest;
import com.nosliw.core.application.common.standalone.HAPStandaloneResponse;
import com.nosliw.core.application.entity.brick.HAPDeserilizerBundle;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.configure.global.HAPConfigureGlobalApplication;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPServiceStory {

	@Autowired
	private HAPConfigureGlobalApplication m_globalAppConfigure;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;

	@Autowired
	private RestTemplate m_restTemplate;
	
	@Autowired
	HAPManagerApplicationBrick m_brickMan; 
	
	@Autowired
	@Qualifier("manual")
	private HAPDeserilizerBundle m_manualSerilizer;
	
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		String responsStr = this.m_restTemplate.getForObject(m_globalAppConfigure.getStoryBaseurl()+"bundle/"+brickId.getBrickTypeId().getBrickType()+"/" + brickId.getBrickTypeId().getVersion() + "/" + brickId.getId(), String.class);
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		return this.m_manualSerilizer.deserializeBundle((JSONObject)serviceData.getData());
	}	

	public HAPServiceData buildStandAlone(List<HAPStandaloneProviderRequest> requests) {
		String responsStr = this.m_restTemplate.postForObject(m_globalAppConfigure.getStoryBaseurl()+"standalone", HAPManagerSerialize.getInstance().toStringValue(requests, HAPSerializationFormat.JSON) ,String.class);
		
		HAPServiceData serviceData = new HAPServiceData();
		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
		
		List<HAPStandaloneResponse> out = new ArrayList<HAPStandaloneResponse>();
		JSONArray responseJsonArray = (JSONArray)serviceData.getData();
		for(int i=0; i<responseJsonArray.length(); i++) {
			out.add((HAPStandaloneResponse)m_entityParseService.parseEntityJSONExplicit(responseJsonArray.getJSONObject(i), HAPStandaloneResponse.ENTITYNAMEFORSERIALIZE));
		}
		
		return HAPServiceData.createSuccessData(out);
	}	

}
