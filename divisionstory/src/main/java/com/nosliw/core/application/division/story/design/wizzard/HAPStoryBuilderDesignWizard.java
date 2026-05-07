package com.nosliw.core.application.division.story.design.wizzard;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.core.application.division.story.design.HAPStoryBuilder;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;

public class HAPStoryBuilderDesignWizard implements HAPStoryBuilder{

	HAPStoryWizzardDefinition m_wizzardDef;
	
	@Override
	public String getBuilderId() {
		return null;
	}

	@Override
	public void initDesign(HAPStoryDesign design) {
		this.m_wizzardDef.initDesign(design);	
	}

	//return all steps
	@Override
	public HAPServiceData buildStory(HAPStoryDesign storyDesign, HAPStoryBuilderRequest changeRequest) {
		HAPStoryWizardRequest wizardRequest = (HAPStoryWizardRequest)changeRequest;

		HAPServiceData out = null;
		
        switch(wizardRequest.getCommand()) {
        case HAPStoryWizardRequest.COMMAND_PREVIOUS:
        	
        	//if no previous, 
        	
        	//roll back current unfinished step, remove current step
        	storyDesign.removeStep();
        	
        	//for previous step, roll back changes in step, remove answer
        	storyDesign.rollBackStep();
        	
        	out = this.createResult(storyDesign);
        	
        	break;
        
        case HAPStoryWizardRequest.COMMAND_NEXT:
        	
        	//if no previous, create a new step with question
        	this.m_wizzardDef.processNext(storyDesign, (HAPStoryWizardRequestNext)wizardRequest.getRequestData());
        	
        	out = this.createResult(storyDesign);
        	
        	break;

        }
		
		return out;
	}
	
	private HAPServiceData createResult(HAPStoryDesign storyDesign) {
		List<HAPStoryDesignMetadataStep> stepsInfo = new ArrayList<HAPStoryDesignMetadataStep>();
		
		stepsInfo.addAll(storyDesign.getStepInfos());
		
		for(int i=stepsInfo.size(); i<m_wizzardDef.getStepsDefinition().size(); i++) {
			stepsInfo.add(new HAPStoryDesignMetadataStepWizard(m_wizzardDef.getStepsDefinition().get(i)));
		}
		
		return HAPServiceData.createSuccessData(stepsInfo);
	}
	
}
