package com.nosliw.core.application.division.manual.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="division.manual")
public class HAPManualConfigure {

	private String path;

	private String sourcePath;

	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path= path;
	}
	
	public String getSourcePath() {
		return this.sourcePath;
	}
	
	public void setSourcePath(String path) {
		this.sourcePath= path;
	}
	
}
