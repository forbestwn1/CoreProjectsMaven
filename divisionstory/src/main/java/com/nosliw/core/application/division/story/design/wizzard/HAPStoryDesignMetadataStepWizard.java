package com.nosliw.core.application.division.story.design.wizzard;

import java.util.List;

import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;

public class HAPStoryDesignMetadataStepWizard implements HAPStoryDesignMetadataStep{

	//step definition
	private HAPStoryWizzardStepDefinition m_stepDefinition;

	//questionair for this step
	private List<HAPStoryWizzardQuestionair> m_questionairs;
	
	public HAPStoryDesignMetadataStepWizard(HAPStoryWizzardStepDefinition stepDefinition) {
		
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
