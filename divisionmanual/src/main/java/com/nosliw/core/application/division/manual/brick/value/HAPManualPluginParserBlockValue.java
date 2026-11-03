package com.nosliw.core.application.division.manual.brick.value;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpSimple;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;

public class HAPManualPluginParserBlockValue extends HAPManualDefinitionPluginParserBrickImpSimple{

	public HAPManualPluginParserBlockValue(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan) {
		super(HAPEnumBrickType.DATA_100, HAPManualDefinitionBlockValue.class, manualDivisionEntityMan, brickMan);
	}

	@Override
	protected void parseSimpleDefinitionContentJson(HAPManualDefinitionBrick brickManual, Object jsonValue, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionBlockValue valueBrick = (HAPManualDefinitionBlockValue)brickManual;
		valueBrick.setValue(jsonValue);
	}
}
