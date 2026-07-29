package com.nosliw.core.application.division.manual.core;

import java.util.List;
import java.util.Map;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public interface HAPManualContentProvider {

	HAPDynamicDefinitionContainer getDynamicDefinition();

	List<HAPEventEmitter> getExposedEvent();
	
	HAPManualInfoContent getMainContent();
	
	Map<String, HAPManualInfoContent> getBranchContents();
	
	HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId);
	
}
