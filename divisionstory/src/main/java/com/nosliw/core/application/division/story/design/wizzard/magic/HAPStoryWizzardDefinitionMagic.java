package com.nosliw.core.application.division.story.design.wizzard.magic;

import java.util.List;
import java.util.Map;

import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizardRequestNext;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardDefinition;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardStepDefinition;

public class HAPStoryWizzardDefinitionMagic extends HAPStoryWizzardDefinition{

	public static final String STEP_SELECTDATASOURCE = "selectDataSource"; 
	public static final String STEP_CUSTOMIZEUI = "customizeUI"; 
	
	
	private Map<String, HAPStoryWizzardStepDefinition> m_steps;
	
	@Override
	public void initDesign(HAPStoryDesign design) {
		
	}
	
	//error: attach error to answer
	//next : next step name, question
	@Override
	public void processNext(HAPStoryDesign storyDesign, HAPStoryWizardRequestNext request) {
		
		//validation lifecycle first
		
		//validation answer
		
		
		//apply answer
		
		
	}

	@Override
	public List<HAPStoryWizzardStepDefinition> getStepsDefinition() {
		// TODO Auto-generated method stub
		return null;
	}


}
