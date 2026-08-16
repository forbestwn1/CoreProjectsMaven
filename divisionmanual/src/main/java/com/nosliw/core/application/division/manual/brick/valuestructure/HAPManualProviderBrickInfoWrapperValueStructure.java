package com.nosliw.core.application.division.manual.brick.valuestructure;

import org.springframework.stereotype.Component;

import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.HAPManualProviderBrickInfoImp;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualInfoBrickType;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBrick;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;

@Component
public class HAPManualProviderBrickInfoWrapperValueStructure extends HAPManualProviderBrickInfoImp{

	public HAPManualProviderBrickInfoWrapperValueStructure(HAPManualManagerBrick manualBrickMan, HAPManagerApplicationBrick brickMan) {
		super(manualBrickMan, brickMan);
	}
	
	@Override
	public HAPIdBrickType getBrickTypeId() {  return HAPManualEnumBrickType.VALUESTRUCTUREWRAPPER_100;   }

	@Override
	protected HAPManualInfoBrickType newBrickTypeInfo() {   return new HAPManualInfoBrickType(false);  }

	@Override
	protected HAPManualDefinitionPluginParserBrick newBrickParser() {
		return new HAPManualPluginParserBrickImpWrapperValueStructure(this.getManualBrickManager(), this.getBrickManager());
	}

	@Override
	protected HAPManualPluginProcessorBrick newBrickProcessor() {   return null;  }

}
