package com.nosliw.core.application.division.manual.brick.interactive.interfacee.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.HAPManualProviderBrickInfoImp;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualInfoBrickType;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBrick;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@Component
public class HAPManualProviderBrickInfoInteractiveInterfaceTask extends HAPManualProviderBrickInfoImp{

	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManualProviderBrickInfoInteractiveInterfaceTask(HAPManualManagerBrick manualBrickMan,
			HAPManagerApplicationBrick brickMan) {
		super(manualBrickMan, brickMan);
	}

	@Override
	public HAPIdBrickType getBrickTypeId() {   return HAPEnumBrickType.INTERACTIVETASKINTERFACE_100;   }

	@Override
	protected HAPManualDefinitionPluginParserBrick newBrickParser() {
		return new HAPManualPluginParserBlockSimpleInteractiveInterfaceTask(this.getManualBrickManager(), this.getBrickManager(), this.m_entityParseService);
	}

	@Override
	protected HAPManualPluginProcessorBrick newBrickProcessor() {
		return new HAPManualPluginProcessorBlockSimpleInteractiveInterfaceTask();
	}

	@Override
	protected HAPManualInfoBrickType newBrickTypeInfo() {  return new HAPManualInfoBrickType(false);  }

}
