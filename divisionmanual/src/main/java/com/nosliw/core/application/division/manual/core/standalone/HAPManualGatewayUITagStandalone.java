package com.nosliw.core.application.division.manual.core.standalone;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPBundle;
import com.nosliw.core.application.HAPResourceDataBrick;
import com.nosliw.core.application.HAPUtilityBrickResource;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.division.manual.core.HAPManualContentProviderText;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
@Component
public class HAPManualGatewayUITagStandalone extends HAPGatewayImp{

	@HAPAttribute
	final public static String COMMAND_BUILD = "build";
	
	@HAPAttribute
	final public static String COMMAND_BUILD_DEINITION = "definition";
	
	@Autowired
	private HAPManualManagerBrick m_manualBrickMan;
	
	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) throws Exception {
		HAPServiceData out = null;
		switch(command){
		case COMMAND_BUILD:
		{
//			String uiTag = parms.getString(COMMAND_BUILD_UITAGID);
			HAPBundle bundle = this.buildStandAloneApplication(null, null, null, runtimeInfo);
			
			HAPResourceDataBrick brickResourceData = HAPUtilityBrickResource.getExportResourceData(bundle, null, null, runtimeInfo);
			
			out = this.createSuccessWithObject(brickResourceData);
			break;
		}
		}
		return out;
	}
	
	//build stand alone application 
	public HAPBundle buildStandAloneApplication(String uiTagId, Map<String, String> tagAttributes, HAPDataDefinition dataDefinition, HAPRuntimeInfo runtimeInfo) {

		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("uiTagName", uiTagId);
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPManualGatewayUITagStandalone.class, "uitag.html");
		String text = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		
		HAPManualContentProviderText contentProvider = new HAPManualContentProviderText(new HAPManualInfoContent(text, HAPSerializationFormat.HTML, null));
		
		return this.m_manualBrickMan.buildBundle(contentProvider, runtimeInfo);
	}

	@Override
	public String getName() {
		return HAPConstantShared.GATEWAY_UITAG_STANDALONE;
	}
	
}
