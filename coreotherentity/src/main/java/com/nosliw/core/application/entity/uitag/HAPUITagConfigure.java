package com.nosliw.core.application.entity.uitag;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="uitag")
public class HAPUITagConfigure {

	private String path;

	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path= path;
	}
	
}
