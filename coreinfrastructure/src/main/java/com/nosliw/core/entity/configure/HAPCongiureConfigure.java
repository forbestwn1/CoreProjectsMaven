package com.nosliw.core.entity.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="configure")
public class HAPCongiureConfigure {

	private String dataPath;

	public String getDataPath() {
		return this.dataPath;
	}
	
	public void setDataPath(String path) {
		this.dataPath= path;
	}
	
}
