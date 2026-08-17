package com.nosliw.core.application.common.manual.gateway.standalone;

import java.util.List;

import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPManualStandalonesBuildRequest  extends HAPSerializableImp{

	List<HAPManualStandaloneRequest> m_Items;
	
	public List<HAPManualStandaloneRequest> getItems(){      return this.m_Items;    }
	
	
}