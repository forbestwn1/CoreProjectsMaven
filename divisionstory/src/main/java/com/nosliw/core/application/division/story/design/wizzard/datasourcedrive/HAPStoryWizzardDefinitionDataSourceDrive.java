package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.core.application.division.story.brick.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.brick.element.HAPStoryElementModule;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignRequestChangeGroup;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemNew;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryDesignMetadataStepWizard;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardRequestDataNext;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardDefinition;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairItemDynamic;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardStepDefinition;

public class HAPStoryWizzardDefinitionDataSourceDrive extends HAPStoryWizzardDefinition{

	public static final String STEP_SELECTDATASOURCE = "selectDataSource"; 
	public static final String STEP_CUSTOMIZEUI = "customizeUI"; 
	
	private final static HAPStoryAliasElement ALIAS_ELEMENT_MODULE = new HAPStoryAliasElement("module", false);
	
	
	private List<HAPStoryWizzardStepDefinition> m_stepDefinitions;
	
	public HAPStoryWizzardDefinitionDataSourceDrive() {
		this.m_stepDefinitions = new ArrayList<HAPStoryWizzardStepDefinition>();
		
		HAPStoryWizzardStepDefinition step1 = new HAPStoryWizzardStepDefinition();
		step1.setId(STEP_SELECTDATASOURCE);
		
		HAPStoryWizzardStepDefinition step2 = new HAPStoryWizzardStepDefinition();
		step2.setId(STEP_CUSTOMIZEUI);

		this.m_stepDefinitions.add(step1);
		this.m_stepDefinitions.add(step2);
	}
	
	@Override
	public void initDesign(HAPStoryDesign design) {
		//init step
		design.newInitStep();
		HAPStoryDesignRequestChangeGroup changeGroup = new HAPStoryDesignRequestChangeGroup();
		changeGroup.addChangeItem(new HAPStoryChangeItemNew(new HAPStoryElementModule(), ALIAS_ELEMENT_MODULE));
		design.applyChanges(changeGroup);
		design.commitStep();

		//service step
		HAPStoryDesignMetadataStepWizard stepMetaData = new HAPStoryDesignMetadataStepWizard(this.getStepDefinition(STEP_SELECTDATASOURCE));
		stepMetaData.addQuestionair(new HAPStoryWizzardQuestionairItemDynamic());
		design.newStep(stepMetaData);
		design.commitStep();
		
	}
	
	//error: attach error to answer
	//next : next step name, question
	@Override
	public void processNext(HAPStoryDesign storyDesign, HAPStoryWizzardRequestDataNext request) {
		
		//validation lifecycle first
		
		//validation answer
		
		
		//apply answer
		
		
	}

	@Override
	public List<HAPStoryWizzardStepDefinition> getStepsDefinition() {
		return this.m_stepDefinitions;
	}
	
	private HAPStoryWizzardStepDefinition getStepDefinition(String stepName) {
		for(HAPStoryWizzardStepDefinition stepDef : this.m_stepDefinitions) {
			if(stepDef.getName().equals(stepName)) {
				return stepDef;
			}
		}
		return null;
	}


}
