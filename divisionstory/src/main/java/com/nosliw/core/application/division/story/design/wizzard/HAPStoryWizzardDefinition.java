package com.nosliw.core.application.division.story.design.wizzard;

import java.util.List;

import com.nosliw.core.application.division.story.design.HAPStoryDesign;

public abstract class HAPStoryWizzardDefinition {

	abstract public List<HAPStoryWizzardStepDefinition> getStepsDefinition();
	
	abstract public void initDesign(HAPStoryDesign design);
	
	//error: attach error to answer
	//next : next step name, question
	abstract public void processNext(HAPStoryDesign storyDesign, HAPStoryWizardRequestNext request);
	
}