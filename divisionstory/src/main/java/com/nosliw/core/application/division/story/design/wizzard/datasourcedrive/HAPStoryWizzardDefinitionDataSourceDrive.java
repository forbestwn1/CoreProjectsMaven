package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizardRequestNext;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardDefinition;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardStepDefinition;

public class HAPStoryWizzardDefinitionDataSourceDrive extends HAPStoryWizzardDefinition{

	public static final String STEP_SELECTDATASOURCE = "selectDataSource"; 
	public static final String STEP_CUSTOMIZEUI = "customizeUI"; 
	
	
	private List<HAPStoryWizzardStepDefinition> m_steps;
	
	public HAPStoryWizzardDefinitionDataSourceDrive() {
		this.m_steps = new ArrayList<HAPStoryWizzardStepDefinition>();
		
		HAPStoryWizzardStepDefinition step1 = new HAPStoryWizzardStepDefinition();
		step1.setId(STEP_SELECTDATASOURCE);
		
		HAPStoryWizzardStepDefinition step2 = new HAPStoryWizzardStepDefinition();
		step2.setId(STEP_SELECTDATASOURCE);

		this.m_steps.add(step1);
		this.m_steps.add(step2);
	}
	
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
		return this.m_steps;
	}


}
