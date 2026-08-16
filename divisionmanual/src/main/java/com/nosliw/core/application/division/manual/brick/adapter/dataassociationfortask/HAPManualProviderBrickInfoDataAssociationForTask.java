package com.nosliw.core.application.division.manual.brick.adapter.dataassociationfortask;

import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.HAPManualProviderBrickInfoImp;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualInfoBrickType;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBrick;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;

@Component
public class HAPManualProviderBrickInfoDataAssociationForTask extends HAPManualProviderBrickInfoImp{

	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManualProviderBrickInfoDataAssociationForTask(HAPManualManagerBrick manualBrickMan,
			HAPManagerApplicationBrick brickMan, HAPServiceParseEntity entityParseService) {
		super(manualBrickMan, brickMan);
		this.m_entityParseService = entityParseService;
	}

	@Override
	public HAPIdBrickType getBrickTypeId() {  return HAPEnumBrickType.DATAASSOCIATIONFORTASK_100;  }

	@Override
	protected HAPManualDefinitionPluginParserBrick newBrickParser() {  
		return new HAPManualPluginParserAdapterDataAssociationForTask(this.getManualBrickManager(), this.getBrickManager(), this.m_entityParseService);
	}

	@Override
	protected HAPManualPluginProcessorBrick newBrickProcessor() {
		return new HAPManaualPluginAdapterProcessorDataAssociationForTask();
	}

	@Override
	protected HAPManualInfoBrickType newBrickTypeInfo() {   return new HAPManualInfoBrickType(false);  }

}
