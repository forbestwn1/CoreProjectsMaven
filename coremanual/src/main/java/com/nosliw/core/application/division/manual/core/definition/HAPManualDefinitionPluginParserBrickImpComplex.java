package com.nosliw.core.application.division.manual.core.definition;

import org.json.JSONObject;

import com.nosliw.core.application.common.brick.HAPIdBrickType;
import com.nosliw.core.application.common.valuecontext.HAPWithValueContext;
import com.nosliw.core.application.division.manual.core.HAPManualEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;

public class HAPManualDefinitionPluginParserBrickImpComplex extends HAPManualDefinitionPluginParserBrickImp{

	public HAPManualDefinitionPluginParserBrickImpComplex(HAPIdBrickType brickTypeId, Class<? extends HAPManualDefinitionBrick> brickClass, HAPManualManagerBrick manualBrickMan, HAPManagerApplicationBrick brickMan) {
		super(brickTypeId, brickClass, manualBrickMan, brickMan);
	}

	@Override
	protected void parseDefinitionContentJson(HAPManualDefinitionBrick brickDefinition, Object jsonValue, HAPManualDefinitionContextParse parseContext) {
		super.parseDefinitionContentJson(brickDefinition, jsonValue, parseContext);
		
		JSONObject jsonObj = (JSONObject)jsonValue;
		
		parseBrickAttributeJson(brickDefinition, jsonObj, HAPWithValueContext.VALUECONTEXT, HAPManualEnumBrickType.VALUECONTEXT_100, null, parseContext);	
		
		this.parseComplexDefinitionContentJson(brickDefinition, jsonObj, parseContext);
	}

	protected void parseComplexDefinitionContentJson(HAPManualDefinitionBrick brickDefinition, JSONObject jsonObj, HAPManualDefinitionContextParse parseContext) {}

}
