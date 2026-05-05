package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignStep;

public class HAPStoryWizzardManager {

	private Map<String, HAPStoryWizzardStep> m_steps;
	
	//error: 
	//next : next step name, question
	public HAPServiceData processNext(HAPStoryDesign storyDesign, HAPStoryWizardRequest request, String nextStep) {
		
		
		
	}
	
	public HAPStoryWizardReponse processCommand(HAPStoryDesign storyDesign, HAPStoryWizardRequest request) {
		
		String command = request.getCommand();
		
		switch(command) {
		case HAPStoryWizardRequest.COMMAND_NEXT:
			HAPStoryWizardRequestNext nextData = (HAPStoryWizardRequestNext)request.getRequestData();
			HAPStoryDesignStep currentStep = storyDesign.getCurrentStep();
			
			
			break;
		}
		
		
	}
	
	
	
}
