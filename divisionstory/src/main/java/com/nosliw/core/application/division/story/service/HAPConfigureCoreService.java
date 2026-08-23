package com.nosliw.core.application.division.story.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="application.nosliw-core")
public class HAPConfigureCoreService {

	private String baseurl;

	public String getBaseurl() {
		return this.baseurl;
	}
	
	public void setBaseurl(String url) {
		this.baseurl = url;
	}
	
}
