package com.nosliw.core.application.entity.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="datasource")
public class HAPDataSourceServiceConfigure {

	private String dataSourceUrl;

	public String getDataSourceUrl() {
		return this.dataSourceUrl;
	}
	
	public void setDataSourceUrl(String url) {
		this.dataSourceUrl = url;
	}
	
}
