package com.nosliw.core.application.division.story.design.wizzard;

import com.nosliw.core.application.division.story.design.HAPStoryBuilder;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseBuild;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseNew;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;

public abstract class HAPStoryBuilderDesignWizard implements HAPStoryBuilder{

	private HAPStoryWizzardDefinition m_wizzardDef;
	
	private String m_builderId;
	
	public HAPStoryBuilderDesignWizard(String builderId, HAPStoryWizzardDefinition wizzardDef) {
		this.m_builderId = builderId;
		this.m_wizzardDef = wizzardDef;
	}
	
	@Override
	public String getBuilderId() {    return this.m_builderId;   }

	@Override
	public HAPStoryBuilderResponseNew initDesign(HAPStoryDesign design) {
		this.m_wizzardDef.initDesign(design);	
    	return this.createResultNew(design);
	}

	//return all steps
	@Override
	public HAPStoryBuilderResponseBuild buildStory(HAPStoryDesign storyDesign, HAPStoryBuilderRequest changeRequest) {
		HAPStoryWizardRequest wizardRequest = (HAPStoryWizardRequest)changeRequest;

		HAPStoryBuilderResponseBuild out = null;
		
        switch(wizardRequest.getCommand()) {
        case HAPStoryWizardRequest.COMMAND_PREVIOUS:
        	
        	//if no previous, 
        	
        	//roll back current unfinished step, remove current step
        	storyDesign.removeStep();
        	
        	//for previous step, roll back changes in step, remove answer
        	storyDesign.rollBackStep();
        	
        	out = this.createResultBuild(storyDesign);
        	
        	break;
        
        case HAPStoryWizardRequest.COMMAND_NEXT:
        	
        	//if no previous, create a new step with question
        	this.m_wizzardDef.processNext(storyDesign, (HAPStoryWizardRequestNext)wizardRequest.getRequestData());
        	
        	out = this.createResultBuild(storyDesign);
        	
        	break;

        }
		
		return out;
	}
	
	private HAPStoryBuilderResponseBuild createResultBuild(HAPStoryDesign storyDesign) {
		HAPStoryBuilderResponseBuild out = new HAPStoryBuilderResponseBuild();
		
		for(HAPStoryDesignMetadataStep stepInfo : storyDesign.getStepInfos()) {
			out.addStepInfo(stepInfo);
		}

		for(int i=storyDesign.getStepInfos().size(); i<m_wizzardDef.getStepsDefinition().size(); i++) {
			out.addStepInfo(new HAPStoryDesignMetadataStepWizard(m_wizzardDef.getStepsDefinition().get(i)));
		}
		
		return out;
	}
	
	private HAPStoryBuilderResponseNew createResultNew(HAPStoryDesign storyDesign) {
		HAPStoryBuilderResponseNew out = new HAPStoryBuilderResponseNew(storyDesign.getId());
		
		for(HAPStoryDesignMetadataStep stepInfo : storyDesign.getStepInfos()) {
			out.addStepInfo(stepInfo);
		}

		for(int i=storyDesign.getStepInfos().size(); i<m_wizzardDef.getStepsDefinition().size(); i++) {
			out.addStepInfo(new HAPStoryDesignMetadataStepWizard(m_wizzardDef.getStepsDefinition().get(i)));
		}
		
		return out;
	}
	
}
