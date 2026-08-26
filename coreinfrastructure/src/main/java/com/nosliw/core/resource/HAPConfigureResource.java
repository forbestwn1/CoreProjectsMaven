package com.nosliw.core.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="resource")
public class HAPConfigureResource {
	
	public final static String LOADRESOURCEBYFILE_MODE_NEVER = "never";
	public final static String LOADRESOURCEBYFILE_MODE_ALWAYS = "always";
	public final static String LOADRESOURCEBYFILE_MODE_DEPENDS = "depends";
	
	//resource info parm name for how to load resource : value / file
	public static final String RESOURCE_LOADPATTERN = "loadPattern";
	public static final String RESOURCE_LOADPATTERN_VALUE = "value";
	public static final String RESOURCE_LOADPATTERN_FILE = "file";

	private boolean cached;
	private String fileLoadMode;
	private List<String> fileLoadResources;
	
	public HAPConfigureResource() {
		this.cached = false;
		this.fileLoadMode = LOADRESOURCEBYFILE_MODE_DEPENDS;
		this.fileLoadResources = new ArrayList<String>();
	}
	
	public boolean isCached() {
		return cached;
	}

	public String getFileLoadMode() {
		return fileLoadMode;
	}

	public List<String> getFileLoadResources() {
		return fileLoadResources;
	}

	public void setCached(boolean cached) {
		this.cached = cached;
	}

	public void setFileLoadMode(String fileLoadMode) {
		this.fileLoadMode = fileLoadMode;
	}

	public void setFileLoadResources(List<String> fileLoadResources) {
		this.fileLoadResources = fileLoadResources;
	}

}
