package com.nosliw.core.application.common.standalone;

import java.util.List;

import com.nosliw.common.exception.HAPServiceData;

public interface HAPStandaloneProvider{
	
	String getName();
	
	HAPServiceData buildContent(List<HAPStandaloneProviderRequest> requests);
	
}
