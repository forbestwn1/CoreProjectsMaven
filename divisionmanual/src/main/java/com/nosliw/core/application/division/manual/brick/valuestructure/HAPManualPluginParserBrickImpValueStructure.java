package com.nosliw.core.application.division.manual.brick.valuestructure;

import org.json.JSONObject;

import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.common.structure.HAPUtilityParserStructure;
import com.nosliw.core.application.division.manual.core.HAPManualEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpSimple;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPManualPluginParserBrickImpValueStructure extends HAPManualDefinitionPluginParserBrickImpSimple{

	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManualPluginParserBrickImpValueStructure(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan, HAPServiceParseEntity entityParseService) {
		super(HAPManualEnumBrickType.VALUESTRUCTURE_100, HAPManualDefinitionBrickValueStructure.class, manualDivisionEntityMan, brickMan);
		this.m_entityParseService = entityParseService;
	}

	@Override
	protected void parseSimpleDefinitionContentJson(HAPManualDefinitionBrick entityDefinition, Object jsonValue, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionBrickValueStructure manualValueStructure = (HAPManualDefinitionBrickValueStructure)entityDefinition;
		HAPUtilityParserStructure.parseValueStructureJson((JSONObject)jsonValue, manualValueStructure.getValue(), this.m_entityParseService); 
	}
	
}
