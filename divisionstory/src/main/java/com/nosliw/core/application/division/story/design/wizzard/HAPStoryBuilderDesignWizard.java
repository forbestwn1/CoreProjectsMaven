package com.nosliw.core.application.division.story.design.wizzard;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.core.application.division.story.HAPStoryStory;
import com.nosliw.core.application.division.story.design.HAPStoryBuilder;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;

public class HAPStoryBuilderDesignWizard implements HAPStoryBuilder{

	@Override
	public String getBuilderId() {
		return null;
	}

	@Override
	public void initDesign(HAPStoryDesign design) {
		HAPStoryStory story = design.getStory();
		
		
		
	}

	@Override
	public HAPServiceData buildStory(HAPStoryDesign storyDesign, HAPStoryBuilderRequest changeRequest) {
		HAPStoryWizardRequest wizardRequest = (HAPStoryWizardRequest)changeRequest;
		
        switch(wizardRequest.getCommand()) {
        case HAPStoryWizardRequest.COMMAND_PREVIOUS:
        	storyDesign
        	
        	break;
        
        case HAPStoryWizardRequest.COMMAND_NEXT:
        	break;
        }
		
		
		return null;
	}

}
