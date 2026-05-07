package com.nosliw.core.application.division.story.design.wizzard;

import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;

public class HAPStoryWizardRequest extends HAPStoryBuilderRequest{

	public static final String COMMAND_NEXT = "next";
	public static final String COMMAND_PREVIOUS = "previous";
	
	private String m_command;
	
	//answer
	private Object m_requestData;
	

	public String getCommand() {
		return this.m_command;
	}

	public Object getRequestData() {
		return this.m_requestData;
	}
	
}
