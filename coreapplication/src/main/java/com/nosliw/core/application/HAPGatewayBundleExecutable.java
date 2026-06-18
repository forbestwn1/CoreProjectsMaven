package com.nosliw.core.application;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPGatewayBundleExecutable extends HAPGatewayImp{

	@HAPAttribute
	final public static String COMMAND_LOADEXECUTABLEBUNDLE = "loadExecutableBundle";

	@HAPAttribute
	final public static String COMMAND_LOADEXECUTABLEBUNDLE_BRICKID = "brickId";
	
	@HAPAttribute
	final public static String COMMAND_LOADEXECUTABLEBUNDLE_EXPORTNAME = "exportName";
	
	@Autowired
	private HAPManagerApplicationBrick m_brickManager;
	
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
				out = this.createSuccessWithObject(bundleForExecutable);
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
