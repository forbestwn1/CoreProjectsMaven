package com.nosliw.core.application.division.story.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.nosliw-story.datasource")
public class HAPConfigureStoryDataSource {

	private List<String> tag = new ArrayList<String>();
	
	public List<String> getTag() {		return this.tag;	}
	
	public void setTag(List<String> tag) {		this.tag= tag;	}
	
}
