package com.nosliw.core.application.brick;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.nosliw-core.bundle")
public class HAPBundleConfigure {

	private boolean export;
	
	private String exportPath;

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public String getExportPath() {
		return this.exportPath;
	}
	
	public void setExportPath(String path) {
		this.exportPath= path;
	}
	
}
