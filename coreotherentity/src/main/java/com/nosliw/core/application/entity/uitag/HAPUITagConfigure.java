package com.nosliw.core.application.entity.uitag;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="uitag")
public class HAPUITagConfigure {

	private String path;
	
	public String getPath() {
		return this.path;
	}
	
	
}
