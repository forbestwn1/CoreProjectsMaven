package com.nosliw.core.application.division.manual.brick.valuestructure;

import com.nosliw.core.application.division.manual.core.HAPManualEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpSimple;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;

public class HAPManualPluginParserBrickImpWrapperValueStructure extends HAPManualDefinitionPluginParserBrickImpSimple{

	public HAPManualPluginParserBrickImpWrapperValueStructure(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan) {
		super(HAPManualEnumBrickType.VALUESTRUCTUREWRAPPER_100, HAPManualDefinitionBrickWrapperValueStructure.class, manualDivisionEntityMan, brickMan);
	}

}
