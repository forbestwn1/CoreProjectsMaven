package com.nosliw.core.application.common.manual.gateway.standalone;

import java.util.List;

import com.nosliw.common.exception.HAPServiceData;

public interface HAPManualStandaloneProvider{
	
	String getName();
	
	HAPServiceData buildContent(List<HAPManualStandaloneProviderRequest> requests);
	
}
