package com.nosliw.core.application.resource;

import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPBundleForExecute;
import com.nosliw.core.resource.HAPResourceDataImp;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
public class HAPResourceDataBrick extends HAPResourceDataImp{

	@HAPAttribute
	public final static String BUNDLE = "bundle"; 
	
	private HAPBundleForExecute m_bundle;
	
	public HAPResourceDataBrick(HAPBundleForExecute bundle) {
		this.m_bundle = bundle;
	}

	public HAPBundleForExecute getBundle() {     return this.m_bundle;     }
	
	@Override
	public void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo) {	
		this.getBundle().getBrick().buildResourceDependency(dependency, runtimeInfo);
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(BUNDLE, this.m_bundle.toStringValue(HAPSerializationFormat.JSON));
	}
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(BUNDLE, this.m_bundle.toStringValue(HAPSerializationFormat.JAVASCRIPT));
	}	

}
