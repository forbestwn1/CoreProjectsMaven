package com.nosliw.application.core.external.static1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.service.staticresource.HAPServiceStaticResource;

@Configuration
public class HAPStaticServiceFactory {

	@Autowired
	private HAPConfigureStaticService m_staticConfigure;

	@Autowired
	private RestTemplate m_restTemplate;

	@Autowired
	private HAPServiceParseEntity m_parseServices;
	
	@Bean
	HAPServiceStaticResource getStaticService() {
		return new HAPServiceStaticResource(this.m_staticConfigure.getBaseurl(), this.m_restTemplate, this.m_parseServices);
	}
	
}
