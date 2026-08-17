package com.nosliw.core.application.division.story.design.standalone;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPBundleForExecute;
import com.nosliw.core.application.brick.HAPUtilityBundleForExecute;
import com.nosliw.core.application.common.uitag.HAPUITageQueryData;
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
//@Component
public class HAPStoryGatewayStandalone extends HAPGatewayImp{

	@HAPAttribute
    public static final String GATEWAY = "gatewayStoryStandalone";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE = "createStandalone";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_PARM = "parm";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_PARM_UITAGQUERY = "uiTagQuery";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_PARM_ID = "resourceId";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_PARM_DOMAIN = "domain";

	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	@Autowired
	HAPRuntimeManager m_runtimeManager;
	
	@Autowired
	private HAPServiceIdGenerator m_idGeneratorService;
	
	@Override
	public String getName() {  return GATEWAY;  }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		HAPServiceData out = null;
		switch(command) {
		case COMMAND_CEATESTANDALONE:

			Map<HAPResourceId, HAPResourceInfo> resourcesInfo = new LinkedHashMap<HAPResourceId, HAPResourceInfo>();
			List<HAPResource> resources = new ArrayList<HAPResource>();
			
			JSONArray parmsArray = parms.getJSONArray(COMMAND_CEATESTANDALONE_PARM);
			for(int i=0; i<parmsArray.length(); i++) {
				JSONObject parmJsonObj = parmsArray.getJSONObject(i);
				JSONObject uiTagQueryJsonObj = parmJsonObj.getJSONObject(COMMAND_CEATESTANDALONE_PARM_UITAGQUERY);
				HAPUITageQueryData uiTagQuery = HAPUITageQueryData.parseUITagQueryData(uiTagQueryJsonObj, m_entityParseService);
				HAPBundleForBrick bundleForBrick = HAPStoryUtilityUITag.buildStandaloneBundleForUITag(uiTagQuery, this.m_uiTagMan, this.m_standaloneMan, HAPRuntimeManager.RUNTIME_JS_BROWSER);
				HAPBundleForExecute bundleForExecutable = HAPUtilityBundleForExecute.toBundleExecutable(bundleForBrick, null);
				
				String id = (String)parmJsonObj.opt(COMMAND_CEATESTANDALONE_PARM_ID);
				if(id==null) {
					String domain = (String)parmJsonObj.opt(COMMAND_CEATESTANDALONE_PARM_DOMAIN);
					id = this.m_idGeneratorService.generateIdStr();
					id = HAPUtilityNamingConversion.cascadeNameSegment(domain, id);
				}
				
				HAPResourceId resourceId = new HAPResourceIdSimple(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TRANSIENT, "1.0.0", id);
				HAPResourceData resourceData = new HAPResourceDataImpTransient(bundleForExecutable);
				HAPResource resource = new HAPResource(resourceId, resourceData, null);
				
				resourcesInfo.put(resourceId, new HAPResourceInfo(resourceId));
				resources.add(resource);
			}
			
			HAPGatewayOutput gatewayOutput = (HAPGatewayOutput)this.m_runtimeManager.getLoadResourceAdapter(runtimeInfo).buildLoadResourceData(resourcesInfo, resources);
			out = HAPServiceData.createSuccessData(gatewayOutput);
			
//			Thread.sleep(5000);
			
			break;
		}

		return out;
	}

}
