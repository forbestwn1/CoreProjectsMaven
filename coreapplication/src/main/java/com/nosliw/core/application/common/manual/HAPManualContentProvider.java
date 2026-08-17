package com.nosliw.core.application.common.manual;

import java.util.List;
import java.util.Map;

import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public interface HAPManualContentProvider extends HAPSerializable{

	HAPDynamicDefinitionContainer getDynamicDefinition();

	List<HAPEventEmitter> getExposedEvent();
	
	List<HAPCommandProcess> getExposedCommand();
	
	HAPManualInfoContent getMainContent();
	
	Map<String, HAPManualInfoContent> getBranchContents();
	
	HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId);
	
}
