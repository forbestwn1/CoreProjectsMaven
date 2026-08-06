package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryBuilderDesignWizard;
import com.nosliw.core.application.entity.datasource.HAPManagerService;
import com.nosliw.core.application.entity.uitag.HAPManagerUITag;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.HAPDataTypeManager;

@Component
public class HAPStoryBuilderDesignWizardDataSourceDrive extends HAPStoryBuilderDesignWizard{

	public HAPStoryBuilderDesignWizardDataSourceDrive(HAPServiceParseEntity entityParseService, HAPManagerService serviceMan, HAPDataTypeHelper dataTypeHelper, HAPDataTypeManager dataTypeMan, HAPManagerUITag uiTagMan) {
		super(HAPConstantShared.STORY_BUILDER_DATASOURCEDRIVE, new HAPStoryWizzardDefinitionDataSourceDrive(entityParseService, serviceMan, dataTypeHelper, dataTypeMan, uiTagMan), entityParseService);
	}

}
