package com.nosliw.core.application.entity.uitag;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="uitag")
public class HAPConfigureUITag {

	private String definitionPath;

	public String getDefinitionPath() {
		return this.definitionPath;
	}
	
	public void setDefinitionPath(String path) {
		this.definitionPath= path;
	}
	
}
