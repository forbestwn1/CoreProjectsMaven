package com.nosliw.core.application.division.story.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.nosliw-story.persistence")
public class HAPConfigureStory {

	private String path;
	
	public String getPath() {		return this.path;	}
	
	public void setPath(String path) {		this.path= path;	}
	
}
