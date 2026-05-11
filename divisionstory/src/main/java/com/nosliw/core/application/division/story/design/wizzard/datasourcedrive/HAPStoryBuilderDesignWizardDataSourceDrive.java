package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryBuilderDesignWizard;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@Component
public class HAPStoryBuilderDesignWizardDataSourceDrive extends HAPStoryBuilderDesignWizard{

	public HAPStoryBuilderDesignWizardDataSourceDrive(HAPServiceParseEntity entityParseService) {
		super(HAPConstantShared.STORY_BUILDER_DATASOURCEDRIVE, new HAPStoryWizzardDefinitionDataSourceDrive(entityParseService), entityParseService);
	}

}
