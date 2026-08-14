package com.nosliw.core.application.division.manual.common.contentprovider;

import java.util.List;
import java.util.Map;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public interface HAPManualContentProvider {

	HAPDynamicDefinitionContainer getDynamicDefinition();

	List<HAPEventEmitter> getExposedEvent();
	
	List<HAPCommandProcess> getExposedCommand();
	
	HAPManualInfoContent getMainContent();
	
	Map<String, HAPManualInfoContent> getBranchContents();
	
	HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId);
	
}
