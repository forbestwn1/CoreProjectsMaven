package com.nosliw.core.application.division.story.design;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="division.story")
public class HAPStoryConfigure {

	private String path;

	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path= path;
	}
	
}
