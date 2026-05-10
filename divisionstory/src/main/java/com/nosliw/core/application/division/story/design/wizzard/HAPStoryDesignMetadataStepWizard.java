package com.nosliw.core.application.division.story.design.wizzard;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;

public class HAPStoryDesignMetadataStepWizard extends HAPSerializableImp implements HAPStoryDesignMetadataStep{

	//step definition
	private HAPStoryWizzardStepDefinition m_stepDefinition;

	//questionair for this step
	private List<HAPStoryWizzardQuestionair> m_questionairs;
	
	public HAPStoryDesignMetadataStepWizard(HAPStoryWizzardStepDefinition stepDefinition) {
		this.m_questionairs = new ArrayList<HAPStoryWizzardQuestionair>();
	    this.m_stepDefinition = stepDefinition;
	}
	
	public void addQuestionair(HAPStoryWizzardQuestionair questionair) {
		this.m_questionairs.add(questionair);
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
