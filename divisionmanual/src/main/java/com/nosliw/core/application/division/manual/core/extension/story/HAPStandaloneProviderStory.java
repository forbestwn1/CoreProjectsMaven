package com.nosliw.core.application.division.manual.core.extension.story;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneProvider;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneProviderRequest;

@Component
public class HAPStandaloneProviderStory implements HAPManualStandaloneProvider{

	@Autowired
	private HAPServiceStory m_storyService;
	
	@Override
	public String getName() {    return HAPConstantShared.STANDALONE_PROVIDER_STORY;   }

	@Override
	public HAPServiceData buildContent(List<HAPManualStandaloneProviderRequest> requests) {
		return this.m_storyService.buildStandAlone(requests);
	}

}
