package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryBuilderDesignWizard;

@Component
public class HAPStoryBuilderDesignWizardDataSourceDrive extends HAPStoryBuilderDesignWizard{

	public HAPStoryBuilderDesignWizardDataSourceDrive() {
		super(HAPConstantShared.STORY_BUILDER_DATASOURCEDRIVE, new HAPStoryWizzardDefinitionDataSourceDrive());
	}

}
