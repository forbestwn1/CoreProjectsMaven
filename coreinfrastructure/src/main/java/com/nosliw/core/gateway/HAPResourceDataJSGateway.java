package com.nosliw.core.gateway;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.resource.HAPResourceDataJSValueImp;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPResourceDataJSGateway extends HAPResourceDataJSValueImp{

	@HAPAttribute
	public static String GATEWAY = "gateway";

	private String m_name;
	
	public HAPResourceDataJSGateway(String name){
		this.m_name = name;
	}
	
	@Override
	public String getValue() {
		
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("gatewayId", this.m_name);
		InputStream javaTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPResourceDataJSGateway.class, "GatewayResource.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		return script;
	}

	@Override
	public HAPResourceDataOrWrapper getDescendant(HAPPath path) {
		return null;
	}

	@Override
	public List<HAPResourceDependency> getResourceDependency(HAPRuntimeInfo runtimeInfo) {
		return new ArrayList<HAPResourceDependency>();
	}

	
	@Override
	protected String buildJavascript(){ return this.getValue(); }

//	@Override
//	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
//		super.buildJsonMap(jsonMap, typeJsonMap);
//		jsonMap.put(VALUE, this.getValue());
//		typeJsonMap.put(VALUE, HAPJsonTypeScript.class);
//	}
}
