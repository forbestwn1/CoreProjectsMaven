package com.nosliw.core.application.entity.jslibrary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="codtable")
public class HAPCodeTableConfigure {

	private String path;

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path= path;
	}
	
}
