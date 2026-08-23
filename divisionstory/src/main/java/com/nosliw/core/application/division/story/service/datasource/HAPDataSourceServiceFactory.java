package com.nosliw.core.application.division.story.service.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.datasource.HAPServiceDataSource;

@Configuration
public class HAPDataSourceServiceFactory {

	@Autowired
	private HAPConfigureDataSourceService m_dataSourceConfigure;

	@Autowired
	private HAPServiceParseEntity m_entityParseService;

	@Autowired
	private RestTemplate m_restTemplate;
	
	@Bean
	HAPServiceDataSource getDataSourceService() {
		return new HAPServiceDataSource(this.m_dataSourceConfigure.getBaseurl(), this.m_entityParseService, this.m_restTemplate);
	}
	
}
