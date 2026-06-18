package com.nosliw.core.application.division.manual.core;

import java.util.Map;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public interface HAPManualContentProvider {

	HAPDynamicDefinitionContainer getDynamicDefinition();
	
	HAPManualInfoContent getMainContent();
	
	Map<String, HAPManualInfoContent> getBranchContents();
	
	HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId);
	
}
