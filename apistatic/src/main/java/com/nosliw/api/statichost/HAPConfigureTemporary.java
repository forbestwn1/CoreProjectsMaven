package com.nosliw.api.statichost;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.nosliw-static.temporary")
public class HAPConfigureTemporary {

	private String url;
	
	private String path;
	
	public String getUrl() {   return this.url;    }
	
	public void setUrl(String url) {    this.url = url;        }
	
	public String getPath() {   return this.path;    }
	
	public void setPath(String path) {    this.path = path;        }
	
}
