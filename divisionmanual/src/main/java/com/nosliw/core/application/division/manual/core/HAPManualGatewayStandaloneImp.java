package com.nosliw.core.application.division.manual.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPBundleForExecute;
import com.nosliw.core.application.brick.HAPUtilityBundleForExecute;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualGatewayStandalone;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneProvider;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneProviderRequest;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneRequest;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneResponse;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandalonesBuildRequest;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.gateway.HAPGatewayOutput;
import com.nosliw.core.resource.HAPResource;
import com.nosliw.core.resource.HAPResourceData;
import com.nosliw.core.resource.HAPResourceDataImpTransient;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.resource.HAPResourceInfo;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.runtime.HAPRuntimeManager;
import com.nosliw.core.service.idgenerator.HAPServiceIdGenerator;

@Component
public class HAPManualGatewayStandaloneImp extends HAPGatewayImp implements HAPManualGatewayStandalone{

	private Map<String, HAPManualStandaloneProvider> m_providers;
	
	@Autowired
	private HAPManualManagerBrick m_manualBrickMan;

	@Autowired
	HAPRuntimeManager m_runtimeManager;

	@Autowired
	private HAPServiceIdGenerator m_idGeneratorService;
	

	public HAPManualGatewayStandaloneImp() {
		this.m_providers = new LinkedHashMap<String, HAPManualStandaloneProvider>();
	}
	
	@Autowired(required=false)
	private void setProviders(List<HAPManualStandaloneProvider> providers) {
		for(HAPManualStandaloneProvider provider : providers) {
			this.m_providers.put(provider.getName(), provider);
		}
	}
	
	@Override
	public String getName() {    return HAPConstantShared.GATEWAY_MANUAL_STANDALONE;    }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		JSONObject qurestJsonObj = parms.optJSONObject(PARMS_REQUEST);
		HAPManualStandalonesBuildRequest request = new HAPManualStandalonesBuildRequest();
		request.buildObject(qurestJsonObj, HAPSerializationFormat.JSON);
		
		Map<String, List<HAPManualStandaloneProviderRequest>> sortedReqeust = new LinkedHashMap<>();
		List<HAPManualStandaloneRequest> items = request.getItems();
		for(HAPManualStandaloneRequest item : items) {
			String providerName = item.getProviderName();
			List<HAPManualStandaloneProviderRequest> byProvider = sortedReqeust.get(providerName);
			if(byProvider==null) {
				byProvider = new ArrayList<>();
				sortedReqeust.put(providerName, byProvider);
			}
			byProvider.add(item.getProviderRequest());
		}
		
		List<HAPManualStandaloneResponse> allContent = new ArrayList<HAPManualStandaloneResponse>();
		for(String providerName : sortedReqeust.keySet()) {
			HAPServiceData serviceData = this.m_providers.get(providerName).buildContent(sortedReqeust.get(providerName));
			List<HAPManualStandaloneResponse> providerResponse = (List<HAPManualStandaloneResponse>)serviceData.getData();

			//if response not provide id, generate id
			for(int i=0; i<providerResponse.size(); i++) {
				HAPManualStandaloneResponse response = providerResponse.get(i);
				String id = response.getId();
				if(id==null) {
					id = sortedReqeust.get(providerName).get(i).getIdPrefix() + this.m_idGeneratorService.generateIdStr();
					response.setId(id);
				}
			}
			
			allContent.addAll(providerResponse);
		}
		
		Map<HAPResourceId, HAPResourceInfo> resourcesInfo = new LinkedHashMap<HAPResourceId, HAPResourceInfo>();
		List<HAPResource> resources = new ArrayList<HAPResource>();
		for(HAPManualStandaloneResponse response : allContent) {
			HAPBundleForBrick bundleForBrick = m_manualBrickMan.buildBundle(response.getContentProvider(), runtimeInfo);
			HAPBundleForExecute bundleForExecutable = HAPUtilityBundleForExecute.toBundleExecutable(bundleForBrick, null);
		
			HAPResourceId resourceId = new HAPResourceIdSimple(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TRANSIENT, "1.0.0", response.getId());
			HAPResourceData resourceData = new HAPResourceDataImpTransient(bundleForExecutable);
			HAPResource resource = new HAPResource(resourceId, resourceData, null);
			
			resourcesInfo.put(resourceId, new HAPResourceInfo(resourceId));
			resources.add(resource);
		}
		
		HAPGatewayOutput gatewayOutput = (HAPGatewayOutput)this.m_runtimeManager.getLoadResourceAdapter(runtimeInfo).buildLoadResourceData(resourcesInfo, resources);
		return HAPServiceData.createSuccessData(gatewayOutput);
	}
}
