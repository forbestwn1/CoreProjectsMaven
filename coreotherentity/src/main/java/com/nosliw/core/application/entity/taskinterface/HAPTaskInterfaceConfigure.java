package com.nosliw.core.application.entity.taskinterface;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="taskinterface")
public class HAPTaskInterfaceConfigure {

	private String path;

	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path= path;
	}
	
}
