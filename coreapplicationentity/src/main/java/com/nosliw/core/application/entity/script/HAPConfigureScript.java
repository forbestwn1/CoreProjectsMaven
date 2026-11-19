package com.nosliw.core.application.entity.script;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="script")
public class HAPConfigureScript {

	private String definitionPath;

	public String getDefinitionPath() {
		return this.definitionPath;
	}
	
	public void setDefinitionPath(String path) {
		this.definitionPath= path;
	}
	
}
