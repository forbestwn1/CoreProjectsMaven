package com.nosliw.api.statichost;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.nosliw-static.static")
public class HAPConfigureStatic {

	private String url;
	
	private boolean consolidate = false;

	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean getConsolidate() {
		return this.consolidate;
	}
	
	public void setConsolidate(boolean consolidate) {
		this.consolidate = consolidate;
	}
	
}
