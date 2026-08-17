package com.nosliw.core.application.common.manual.gateway.standalone;

import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPManualStandaloneProviderRequest extends HAPSerializableImp{
	
	private String m_idPrefix;
	
	private String m_id;
	
	private Object m_parms;

	public String getIdPrefix() {     return this.m_idPrefix;      }
	
}
