package com.nosliw.core.application.division.story.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="gateway")
public class HAPGatewayServiceConfigure {

	private String urlSingle;

	public String getUrlSingle() {
		return this.urlSingle;
	}
	
	public void setUrlSingle(String url) {
		this.urlSingle = url;
	}
	
}
