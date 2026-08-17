package com.nosliw.core.application.division.story.design;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="division.story")
public class HAPStoryConfigure {

	private String path;
	
	private String gatewayUrl;

	public String getPath() {		return this.path;	}
	
	public void setPath(String path) {		this.path= path;	}
	
	public String getGatewayUrl() {     return this.gatewayUrl;     }
	
	public void setGatewayUrl(String url) {       this.gatewayUrl = url;          }
	
}
