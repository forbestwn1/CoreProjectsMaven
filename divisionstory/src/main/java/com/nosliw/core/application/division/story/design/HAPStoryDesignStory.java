package com.nosliw.core.application.division.story.design;

import java.util.List;

import com.nosliw.core.application.division.story.HAPStoryStory;

//dynamic, 
//    store all the information about how story was build
//    support transaction
public class HAPStoryDesignStory {

	private String m_builderId;
	
	private HAPStoryStory m_story;
	
	private List<HAPStoryDesignStep> m_changeHistory;
	
	public HAPStoryInfoStep applyStep(HAPStoryDesignStep step) {
		
	}
	
	private void rollBack(String stepId) {
		
	}
	
	private HAPStoryDesignStep getStepById(String stepId) {}
	
	private String getStepId(HAPStoryInfoStep stepInfo) {}
	
	private String getNextStep(String stepId) {}
	
	private String getPreviousStep(String stepId) {}

	private String getFirstStep() {}
	
	private String getLastStep() {}
	
}
