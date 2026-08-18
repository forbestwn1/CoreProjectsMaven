package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datasource.HAPServiceDataSource;
import com.nosliw.core.application.division.story.api.HAPStoryService;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryBuilderDesignWizard;
import com.nosliw.core.application.division.story.service.uitag.HAPUITagService;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.HAPDataTypeManager;

@Component
public class HAPStoryBuilderDesignWizardDataSourceDrive extends HAPStoryBuilderDesignWizard{

	public HAPStoryBuilderDesignWizardDataSourceDrive(HAPServiceParseEntity entityParseService, HAPDataTypeHelper dataTypeHelper, HAPDataTypeManager dataTypeMan, HAPUITagService uiTagService, HAPServiceDataSource dataSourceService, HAPStoryService storyService) {
		super(HAPConstantShared.STORY_BUILDER_DATASOURCEDRIVE, new HAPStoryWizzardDefinitionDataSourceDrive(entityParseService, dataTypeHelper, dataTypeMan, uiTagService, dataSourceService, storyService), entityParseService);
	}

}
