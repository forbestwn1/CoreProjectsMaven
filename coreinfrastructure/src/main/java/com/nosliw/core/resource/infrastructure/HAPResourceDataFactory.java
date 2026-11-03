package com.nosliw.core.resource.infrastructure;

import java.util.List;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.resource.HAPResourceDataJSValue;
import com.nosliw.core.resource.HAPResourceDataJSValueImp;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPResourceDataFactory {

	public static HAPResourceDataJSValue createJSValueResourceData(String value) {
		return new HAPResourceDataJSValueInternal(value);
	}
 
}

class HAPResourceDataJSValueInternal extends HAPResourceDataJSValueImp{

	private String m_jsValue;
	
	public HAPResourceDataJSValueInternal(String value) {
		this.m_jsValue = value;
	}
	
	@Override
	public String getValue() {  return this.m_jsValue;  }

	@Override
	public HAPResourceDataOrWrapper getDescendant(HAPPath path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HAPResourceDependency> getResourceDependency(HAPRuntimeInfo runtimeInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
