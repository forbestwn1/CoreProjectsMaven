package com.nosliw.core.application.entity.codetable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="codetable")
public class HAPConfigureCodeTable {

	private String dataPath;

	public String getDataPath() {
		return this.dataPath;
	}

	public void setDataPath(String path) {
		this.dataPath= path;
	}
	
}
