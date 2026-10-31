package com.nosliw.application.core.external.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.datasource.HAPServiceDataSource;
import com.nosliw.core.application.entity.configure.global.HAPConfigureGlobalApplication;

@Configuration
public class HAPDataSourceServiceFactory {

	@Autowired
	private HAPConfigureGlobalApplication m_globalAppConfigure;

	@Autowired
	private HAPServiceParseEntity m_entityParseService;

	@Autowired
	private RestTemplate m_restTemplate;

	@Bean
	HAPServiceDataSource getDataSourceService() {
		return new HAPServiceDataSource(this.m_globalAppConfigure.getDatasourceBaseurl(), this.m_entityParseService, this.m_restTemplate);
	}
	
}
