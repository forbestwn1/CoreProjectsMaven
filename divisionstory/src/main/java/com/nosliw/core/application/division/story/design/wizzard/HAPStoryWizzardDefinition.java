package com.nosliw.core.application.division.story.design.wizzard;

import java.util.List;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;

public abstract class HAPStoryWizzardDefinition {

	abstract public List<HAPStoryWizzardStepDefinition> getStepsDefinition();
	
	abstract public void initDesign(HAPStoryDesign design);
	
	//error: attach error to answer
	//next : next step name, question
	abstract public void processNext(HAPStoryDesign storyDesign, HAPStoryWizzardRequestDataNext request);
	
    protected void newStep(HAPStoryDesign design, HAPStoryDesignMetadataStepWizard stepMetaData) {
    	
		design.newStep(stepMetaData);

		//assign id to each questionair
		this.assignIdToQuestionair(design, stepMetaData.getQuestionair());
    }

    private void assignIdToQuestionair(HAPStoryDesign storyDesign, HAPStoryWizzardQuestionair questionair) {
    	questionair.setId(storyDesign.generateId());
    	
    	String qType = questionair.getType();
    	if(qType.equals(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_GROUP)) {
    		HAPStoryWizzardQuestionairGroup groupQ = (HAPStoryWizzardQuestionairGroup)questionair;
    		for(HAPStoryWizzardQuestionair item : groupQ.getItems()) {
    			assignIdToQuestionair(storyDesign, item);
    		}
    	}
    }
}
