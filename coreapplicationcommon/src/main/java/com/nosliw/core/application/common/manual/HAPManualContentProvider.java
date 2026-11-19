package com.nosliw.core.application.common.manual;

import java.util.List;
import java.util.Map;

import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.core.application.common.brick.HAPIdBrick;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.dynamic.HAPDynamicDefinitionContainer;
import com.nosliw.core.application.common.event.HAPEventEmitter;

public interface HAPManualContentProvider extends HAPSerializable{

	public static final String TYPE = "type";
	
	String getType();
	
	HAPDynamicDefinitionContainer getDynamicDefinition();

	List<HAPEventEmitter> getExposedEvent();
	
	List<HAPCommandProcess> getExposedCommand();
	
	HAPManualInfoContent getMainContent();
	
	Map<String, HAPManualInfoContent> getBranchContents();
	
	HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId);
	
}
