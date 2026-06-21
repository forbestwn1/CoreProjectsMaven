package com.nosliw.core.application.division.story.design.uitag;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPBundleForExecute;
import com.nosliw.core.application.HAPUtilityBundleForExecute;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPParserDataDefinition;
import com.nosliw.core.application.division.manual.core.standalone.HAPManualManangerStandalone;
import com.nosliw.core.application.entity.uitag.HAPManagerUITag;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.resource.HAPResource;
import com.nosliw.core.resource.HAPResourceData;
import com.nosliw.core.resource.HAPResourceDataImpTransient;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.resource.HAPResourceInfo;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.runtime.HAPRuntimeManager;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.service.idgenerator.HAPServiceIdGenerator;

@HAPEntityWithAttribute
@Component
public class HAPStoryGatewayStandalone extends HAPGatewayImp{

	@HAPAttribute
    public static final String GATEWAY = "gatewayStoryStandalone";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE = "createStandalone";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_DATADEFINITION = "dataDefinition";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_RESOURCEID = "resourceId";

	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	@Autowired
	private HAPManagerUITag m_uiTagMan;
	
	@Autowired
	private HAPManualManangerStandalone m_standaloneMan;
	
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
			JSONObject dataDefinitionJsonObj = parms.getJSONObject(COMMAND_CEATESTANDALONE_DATADEFINITION);
			HAPDataDefinition dataDefinition = HAPParserDataDefinition.parseDataDefinition(dataDefinitionJsonObj, m_entityParseService);
			HAPBundleForBrick bundleForBrick = HAPStoryUtilityUITag.buildStandaloneBundleForUITag(dataDefinition, this.m_uiTagMan, this.m_standaloneMan, HAPRuntimeManager.RUNTIME_JS_BROWSER);
			HAPBundleForExecute bundleForExecutable = HAPUtilityBundleForExecute.toBundleExecutable(bundleForBrick, null);
			
			String id = (String)parms.opt(COMMAND_CEATESTANDALONE_RESOURCEID);
			if(id==null) {
				id = this.m_idGeneratorService.generateIdStr();
			}
			
			HAPResourceId resourceId = new HAPResourceIdSimple(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TRANSIENT, "1.0.0", "12345678");
			HAPResourceData resourceData = new HAPResourceDataImpTransient(bundleForExecutable);
			HAPResource resource = new HAPResource(resourceId, resourceData, null);
			
			Map<HAPResourceId, HAPResourceInfo> resourcesInfo = new LinkedHashMap<HAPResourceId, HAPResourceInfo>();
			resourcesInfo.put(resourceId, new HAPResourceInfo(resourceId));
			
			out = HAPServiceData.createSuccessData(this.m_runtimeManager.getLoadResourceAdapter(runtimeInfo).buildLoadResourceData(resourcesInfo, List.of(resource)));
			
			break;
		}

		return out;
	}

}
