package com.nosliw.core.application.brick;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="core.bundle")
public class HAPBundleConfigure {

	private String exportPath;

	public String getExportPath() {
		return this.exportPath;
	}
	
	public void setExportPath(String path) {
		this.exportPath= path;
	}
	
}
