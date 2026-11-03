package com.nosliw.core.application.division.manual.brick.scriptexpression.library;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpSimple;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;

public class HAPManualPluginParserBlockScriptExpressionElementInLibrary extends HAPManualDefinitionPluginParserBrickImpSimple{

	public HAPManualPluginParserBlockScriptExpressionElementInLibrary(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan) {
		super(HAPEnumBrickType.SCRIPTEXPRESSIONLIBELEMENT_100, HAPManualBlockScriptExpressionElementInLibrary.class, manualDivisionEntityMan, brickMan);
	}
	
	@Override
	protected void parseSimpleDefinitionContentJson(HAPManualDefinitionBrick brickDefinition, Object jsonValue, HAPManualDefinitionContextParse parseContext) {
		HAPManualBlockScriptExpressionElementInLibrary brick = (HAPManualBlockScriptExpressionElementInLibrary)brickDefinition;
//		HAPManualScriptExpressionLibraryElement value = new HAPManualScriptExpressionLibraryElement();
//		value.buildObject(jsonValue, HAPSerializationFormat.JSON);
//		brick.setValue(value);
	}
}
