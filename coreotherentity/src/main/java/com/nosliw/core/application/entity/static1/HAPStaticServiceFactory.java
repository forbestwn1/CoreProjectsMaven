package com.nosliw.core.application.entity.static1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nosliw.core.service.staticresource.HAPServiceStaticResource;

@Configuration
public class HAPStaticServiceFactory {

	@Autowired
	private HAPStaticServiceConfigure m_staticConfigure;

	@Bean
	HAPServiceStaticResource getStaticService() {
		return new HAPServiceStaticResource(this.m_staticConfigure.getUrl());
	}
	
}
