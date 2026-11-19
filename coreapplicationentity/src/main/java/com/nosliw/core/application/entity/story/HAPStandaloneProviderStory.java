package com.nosliw.core.application.entity.story;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.standalone.HAPStandaloneProvider;
import com.nosliw.core.application.common.standalone.HAPStandaloneProviderRequest;

@Component
public class HAPStandaloneProviderStory implements HAPStandaloneProvider{

	@Autowired
	private HAPServiceStory m_storyService;
	
	@Override
	public String getName() {    return HAPConstantShared.STANDALONE_PROVIDER_STORY;   }

	@Override
	public HAPServiceData buildContent(List<HAPStandaloneProviderRequest> requests) {
		return this.m_storyService.buildStandAlone(requests);
	}

}
