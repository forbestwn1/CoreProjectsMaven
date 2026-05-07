package com.nosliw.core.application.division.story.design.wizzard;

import java.util.List;

public class HAPStoryWizardReponse{

	public static final String COMMAND_NEXT = "next";
	public static final String COMMAND_PREVIOUS = "previous";
	public static final String COMMAND_ERROR = "error";
	
	private String m_command;

	//current steps stack
	private List<String> m_steps;
	
	//question
	private Object m_metadata;
	
	
	
}
