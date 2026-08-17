package com.nosliw.core.application.common.manual.gateway.standalone;

import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPManualStandaloneRequest extends HAPSerializableImp{
	
	private String m_providerName;
	
	private HAPManualStandaloneProviderRequest m_providerRequest;
	
	public String getProviderName() {     return this.m_providerName;      }

	public HAPManualStandaloneProviderRequest getProviderRequest() {     return this.m_providerRequest;        }
}
