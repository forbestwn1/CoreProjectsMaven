package com.nosliw.core.application.entity.story;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="story")
public class HAPStoryServiceConfigure {

	private String url;

	public String geturl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
