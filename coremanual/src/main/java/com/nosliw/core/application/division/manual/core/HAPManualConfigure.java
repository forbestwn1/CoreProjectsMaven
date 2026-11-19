package com.nosliw.core.application.division.manual.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="division.manual")
public class HAPManualConfigure {

	private String executePath;

	private String definitionPath;

	public String getExecutePath() {
		return this.executePath;
	}
	
	public void setExecutePath(String path) {
		this.executePath= path;
	}
	
	public String getDefinitionPath() {
		return this.definitionPath;
	}
	
	public void setDefinitionPath(String path) {
		this.definitionPath= path;
	}
	
}
