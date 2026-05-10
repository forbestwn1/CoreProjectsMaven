package com.nosliw.core.application.division.story.design.wizzard;

public class HAPStoryWizzardQuestionairItemDynamic extends HAPStoryWizzardQuestionairItem{

	private HAPStoryWizzardQuestionairError m_error;

	private Object m_defaultValue;
	
	private Object m_changedValue;
	
	private boolean m_isDirty;
	
	public HAPStoryWizzardQuestionairItemDynamic(Object defaultValue) {
		this.m_defaultValue = defaultValue;
	}
	
	
	public HAPStoryWizzardQuestionairError getError() {
		return this.m_error;
	}
	
}
