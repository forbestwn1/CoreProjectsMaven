package com.nosliw.api.statichost;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties()
public class HAPStaticConfigure {

	private String staticRootUrl;

	private String temporaryStaticRootUrl;
	
	private String directoryTemporary;

	public String getStaticRootUrl() {
		return this.staticRootUrl;
	}
	
	public void setStaticRootUrl(String url) {
		this.staticRootUrl = url;
	}
	
	public String getTemporaryStaticRootUrl() {
		return this.temporaryStaticRootUrl;
	}
	
	public void setTemporaryStaticRootUrl(String url) {
		this.temporaryStaticRootUrl = url;
	}
	
	public String getDirectoryTemporary() {
		return this.directoryTemporary;
	}
	
	public void setDirectoryTemporary(String directory) {
		this.directoryTemporary = directory;
	}
	
}
