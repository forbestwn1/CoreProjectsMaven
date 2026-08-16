package com.nosliw.core.application.brick;

import java.util.List;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
public class HAPAdapterImp extends HAPAdapter{

	private HAPWrapperValue m_valueWrapper;
	
	public HAPAdapterImp() {}
	
	public HAPAdapterImp(HAPWrapperValue valueWrapper) {
		this.m_valueWrapper = valueWrapper;
	}
	
	@Override
	public HAPWrapperValue getValueWrapper() {		return this.m_valueWrapper;	}
	public void setValueWrapper(HAPWrapperValue valueWrapper) {     this.m_valueWrapper = valueWrapper;       }

	@Override
	public void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo) {
		this.m_valueWrapper.buildResourceDependency(dependency, runtimeInfo);
	}

}
