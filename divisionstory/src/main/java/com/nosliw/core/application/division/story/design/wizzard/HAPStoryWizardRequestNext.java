package com.nosliw.core.application.division.story.design.wizzard;

import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;

public class HAPStoryWizardRequestNext extends HAPStoryBuilderRequest{

    private String m_currentStep;
    
    private String m_nextStep;
	
	public String getCurrentStep() {
		return this.m_currentStep;
	}
}
