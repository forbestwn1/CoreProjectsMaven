package com.nosliw.core.application.division.story.design.wizzard;

import org.json.JSONObject;

import com.nosliw.core.application.division.story.design.HAPStoryBuilder;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseBuild;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseNew;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public abstract class HAPStoryBuilderDesignWizard implements HAPStoryBuilder{

	public static final String COMMAND_NEXT = "next";
	public static final String COMMAND_PREVIOUS = "previous";
	
	private HAPServiceParseEntity m_entityParseService;
	
	private HAPStoryWizzardDefinition m_wizzardDef;
	
	private String m_builderId;
	
	public HAPStoryBuilderDesignWizard(String builderId, HAPStoryWizzardDefinition wizzardDef, HAPServiceParseEntity entityParseService) {
		this.m_builderId = builderId;
		this.m_wizzardDef = wizzardDef;
		this.m_entityParseService = entityParseService;
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
		HAPStoryBuilderResponseBuild out = null;
		
        switch(changeRequest.getCommand()) {
        case COMMAND_PREVIOUS:
        	
        	//if no previous,
        	if(!storyDesign.isFirstStep()) {
            	//roll back current unfinished step, remove current step
            	storyDesign.removeStep();
        	}
        	
        	//for previous step, roll back changes in step, remove answer
        	storyDesign.rollBackStep();
        	
        	out = this.createResultBuild(storyDesign);
        	
        	break;
        
        case COMMAND_NEXT:
        	
        	HAPStoryWizzardRequestDataNext nextRequestData = (HAPStoryWizzardRequestDataNext)this.m_entityParseService.parseEntityJSONExplicit((JSONObject)changeRequest.getRequestData(), HAPStoryWizzardRequestDataNext.PARSABLEENTITYTYPE);
        	
        	if(((HAPStoryDesignMetadataStepWizard)storyDesign.getCurrentStep().getMetaData()).getStepDefinition().getName().equals(nextRequestData.getStepData().getStepDefinition().getName())) {
            	storyDesign.rollBackStep();
        	}
        	
        	//if no previous, create a new step with question
        	this.m_wizzardDef.processNext(storyDesign, nextRequestData);
        	
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

		for(int i=out.getStepInfos().size(); i<m_wizzardDef.getStepsDefinition().size(); i++) {
			out.addStepInfo(new HAPStoryDesignMetadataStepWizard(m_wizzardDef.getStepsDefinition().get(i)));
		}
		
		return out;
	}
	
	private HAPStoryBuilderResponseNew createResultNew(HAPStoryDesign storyDesign) {
		HAPStoryBuilderResponseNew out = new HAPStoryBuilderResponseNew(storyDesign.getId());
		
		for(HAPStoryDesignMetadataStep stepInfo : storyDesign.getStepInfos()) {
			out.addStepInfo(stepInfo);
		}

		for(int i=out.getStepInfos().size(); i<m_wizzardDef.getStepsDefinition().size(); i++) {
			out.addStepInfo(new HAPStoryDesignMetadataStepWizard(m_wizzardDef.getStepsDefinition().get(i)));
		}
		
		return out;
	}
	
}
