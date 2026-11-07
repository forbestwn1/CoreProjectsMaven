package com.nosliw.core.utility.rest;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HAPBeanFactoryRestTemplate {

	@Bean
	RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			request.getHeaders().set("Host", request.getURI().getHost());
			return execution.execute(request, body);
		});

		return restTemplate;
	}

}