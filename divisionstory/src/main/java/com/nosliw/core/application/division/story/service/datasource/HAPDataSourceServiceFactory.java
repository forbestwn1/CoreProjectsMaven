package com.nosliw.core.application.division.story.service.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.datasource.HAPServiceDataSource;

@Configuration
public class HAPDataSourceServiceFactory {

	@Autowired
	private HAPDataSourceServiceConfigure m_dataSourceConfigure;

	@Autowired
	private HAPServiceParseEntity m_entityParseService;

	@Bean
	HAPServiceDataSource getDataSourceService() {
		return new HAPServiceDataSource(this.m_dataSourceConfigure.getDataSourceUrl(), this.m_entityParseService);
	}
	
}
