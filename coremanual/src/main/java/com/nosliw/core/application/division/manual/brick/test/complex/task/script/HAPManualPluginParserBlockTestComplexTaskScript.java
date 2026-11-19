package com.nosliw.core.application.division.manual.brick.test.complex.task.script;

import org.json.JSONObject;

import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpComplex;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.application.spec.HAPEnumBrickType;

public class HAPManualPluginParserBlockTestComplexTaskScript extends HAPManualDefinitionPluginParserBrickImpComplex{

	public HAPManualPluginParserBlockTestComplexTaskScript(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan) {
		super(HAPEnumBrickType.TEST_COMPLEX_TASK_SCRIPT_100, HAPManualDefinitionBlockTestComplexTaskScript.class, manualDivisionEntityMan, brickMan);
	}

	@Override
	protected void parseComplexDefinitionContentJson(HAPManualDefinitionBrick entityDefinition, JSONObject jsonObj, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionBlockTestComplexTaskScript taskBrick = (HAPManualDefinitionBlockTestComplexTaskScript)entityDefinition;
	}
}
