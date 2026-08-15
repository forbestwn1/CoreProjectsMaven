package com.nosliw.core.application.entity.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="configure")
public class HAPCongiureConfigure {

	private String path;

	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path= path;
	}
	
}
