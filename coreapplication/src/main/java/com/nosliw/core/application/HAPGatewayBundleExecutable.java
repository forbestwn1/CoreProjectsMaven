package com.nosliw.core.application;

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

@HAPEntityWithAttribute
@Component
public class HAPGatewayBundleExecutable extends HAPGatewayImp{

	@HAPAttribute
	final public static String COMMAND_LOADEXECUTABLEBUNDLE = "loadExecutableBundle";

	@HAPAttribute
	final public static String COMMAND_LOADEXECUTABLEBUNDLE_BRICKID = "brickId";
	
	@HAPAttribute
	final public static String COMMAND_LOADEXECUTABLEBUNDLE_EXPORTNAME = "exportName";
	
	@Autowired
	private HAPManagerApplicationBrick m_brickManager;
	
	@Autowired
	HAPRuntimeManager m_runtimeManager;
	
	
	@Override
	public String getName() {    return HAPConstantShared.GATEWAY_BUNDLEEXECUTABLE;   }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		HAPServiceData out = null;
		try{
			switch(command){
			case COMMAND_LOADEXECUTABLEBUNDLE:
				HAPIdBrick brickId = HAPUtilityBrickId.parseBrickId(parms.get(COMMAND_LOADEXECUTABLEBUNDLE_BRICKID));
				HAPBundleForBrick bundleForBrick = m_brickManager.getBrickBundle(brickId, runtimeInfo);
				HAPBundleForExecute bundleForExecutable = HAPUtilityBundleForExecute.toBundleExecutable(bundleForBrick, (String)parms.opt(COMMAND_LOADEXECUTABLEBUNDLE_EXPORTNAME));
				
				HAPResourceId resourceId = new HAPResourceIdSimple(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TRANSIENT, "1.0.0", brickId.getKey());
				HAPResourceData resourceData = new HAPResourceDataImpTransient(bundleForExecutable);
				HAPResource resource = new HAPResource(resourceId, resourceData, null);
				
				Map<HAPResourceId, HAPResourceInfo> resourcesInfo = new LinkedHashMap<HAPResourceId, HAPResourceInfo>();
				resourcesInfo.put(resourceId, new HAPResourceInfo(resourceId));
				
				HAPGatewayOutput gatewayOutput = (HAPGatewayOutput)this.m_runtimeManager.getLoadResourceAdapter(runtimeInfo).buildLoadResourceData(resourcesInfo, List.of(resource));
				out = HAPServiceData.createSuccessData(gatewayOutput);
				break;
			}
		}
		catch(Exception e){
			out = HAPServiceData.createFailureData(e, "");
			e.printStackTrace();
		}
		return out;
	}

}
