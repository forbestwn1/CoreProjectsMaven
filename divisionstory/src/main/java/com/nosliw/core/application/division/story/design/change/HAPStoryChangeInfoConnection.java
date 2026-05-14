package com.nosliw.core.application.division.story.design.change;

public abstract class HAPStoryChangeInfoConnection {

	private String m_connectionType;
	
	public HAPStoryChangeInfoConnection(String connectionType) {
		this.m_connectionType = connectionType;
	}
	
	public String getConnectionType() {
		return this.m_connectionType;
	}

	
	
}
