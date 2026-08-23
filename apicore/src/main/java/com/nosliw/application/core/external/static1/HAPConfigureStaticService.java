package com.nosliw.application.core.external.static1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="application.nosliw-static")
public class HAPConfigureStaticService {

	private String baseurl;

	public String getBaseurl() {
		return this.baseurl;
	}
	
	public void setBaseurl(String url) {
		this.baseurl = url;
	}
	
}
