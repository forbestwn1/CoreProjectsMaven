package com.nosliw.core.application.entity.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HAPBeanFactory {

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	
}
