package com.nosliw.core.gateway.types.standalone;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.HAPBundleForExecute;
import com.nosliw.core.application.common.brick.util.HAPUtilityBundleForExecute;
import com.nosliw.core.application.common.standalone.HAPStandaloneProvider;
import com.nosliw.core.application.common.standalone.HAPStandaloneProviderRequest;
import com.nosliw.core.application.common.standalone.HAPStandaloneRequest;
import com.nosliw.core.application.common.standalone.HAPStandaloneResponse;
import com.nosliw.core.application.common.standalone.HAPStandalonesBuildRequest;
import com.nosliw.core.application.entity.manual.HAPServiceManual;
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

@HAPEntityWithAttribute
@Component
public class HAPGatewayStandalone extends HAPGatewayImp{

	@HAPAttribute
	static public final String COMMAND_BUILD = "build";

	@HAPAttribute
	static public final String PARMS_REQUEST = "request";

	private Map<String, HAPStandaloneProvider> m_providers;
	
	@Autowired
	private HAPServiceManual m_manualService;

	@Autowired
	HAPRuntimeManager m_runtimeManager;

	@Autowired
	private HAPServiceIdGenerator m_idGeneratorService;
	

	public HAPGatewayStandalone() {
		this.m_providers = new LinkedHashMap<String, HAPStandaloneProvider>();
	}
	
	@Autowired(required=false)
	private void setProviders(List<HAPStandaloneProvider> providers) {
		for(HAPStandaloneProvider provider : providers) {
			this.m_providers.put(provider.getName(), provider);
		}
	}
	
	@Override
	public String getName() {    return HAPConstantShared.GATEWAY_MANUAL_STANDALONE;    }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		if(command.equals(HAPGatewayStandalone.COMMAND_BUILD)) {
			JSONObject qurestJsonObj = parms.optJSONObject(PARMS_REQUEST);
			HAPStandalonesBuildRequest request = new HAPStandalonesBuildRequest();
			request.buildObject(qurestJsonObj, HAPSerializationFormat.JSON);
			
			Map<String, List<HAPStandaloneProviderRequest>> sortedReqeust = new LinkedHashMap<>();
			List<HAPStandaloneRequest> items = request.getItems();
			for(HAPStandaloneRequest item : items) {
				String providerName = item.getProviderName();
				List<HAPStandaloneProviderRequest> byProvider = sortedReqeust.get(providerName);
				if(byProvider==null) {
					byProvider = new ArrayList<>();
					sortedReqeust.put(providerName, byProvider);
				}
				byProvider.add(item.getProviderRequest());
			}
			
			List<HAPStandaloneResponse> allContent = new ArrayList<HAPStandaloneResponse>();
			for(String providerName : sortedReqeust.keySet()) {
				HAPServiceData serviceData = this.m_providers.get(providerName).buildContent(sortedReqeust.get(providerName));
				List<HAPStandaloneResponse> providerResponse = (List<HAPStandaloneResponse>)serviceData.getData();

				//if response not provide id, generate id
				for(int i=0; i<providerResponse.size(); i++) {
					HAPStandaloneResponse response = providerResponse.get(i);
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
			for(HAPStandaloneResponse response : allContent) {
				HAPBundleForBrick bundleForBrick = m_manualService.buildBundle(response.getContentProvider(), runtimeInfo);
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
		return null;
	}	
}

