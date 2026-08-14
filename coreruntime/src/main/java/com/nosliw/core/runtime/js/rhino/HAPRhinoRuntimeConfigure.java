package com.nosliw.core.runtime.js.rhino;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="rhino")
public class HAPRhinoRuntimeConfigure {

	private String scriptExportPath;

	public String getScriptExportPath() {
		return this.scriptExportPath;
	}
	
	public void setScriptExportPath(String path) {
		this.scriptExportPath = path;
	}
	
}
