package com.nosliw.application.datasource.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="taskinterface")
public class HAPConfigureDataSource {

	private String path;

	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path= path;
	}
	
}
