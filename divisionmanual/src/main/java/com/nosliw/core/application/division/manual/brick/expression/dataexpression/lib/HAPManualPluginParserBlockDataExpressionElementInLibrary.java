package com.nosliw.core.application.division.manual.brick.expression.dataexpression.lib;

import org.json.JSONObject;

import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.dataexpression.definition.HAPDefinitionDataExpressionStandAlone;
import com.nosliw.core.application.common.dataexpression.definition.HAPParserDataExpression;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpSimple;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPManualPluginParserBlockDataExpressionElementInLibrary extends HAPManualDefinitionPluginParserBrickImpSimple{

	private HAPParserDataExpression m_dataExpressionParser;
	
	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManualPluginParserBlockDataExpressionElementInLibrary(
			HAPManualManagerBrick manualDivisionEntityMan, 
			HAPManagerApplicationBrick brickMan, 
			HAPParserDataExpression dataExpressionParser,
			HAPServiceParseEntity entityParseService) {
		super(HAPEnumBrickType.DATAEXPRESSIONLIBELEMENT_100, HAPManualDefinitionBlockDataExpressionElementInLibrary.class, manualDivisionEntityMan, brickMan);
		this.m_dataExpressionParser = dataExpressionParser;
		this.m_entityParseService = entityParseService;
	}
	
	@Override
	protected void parseSimpleDefinitionContentJson(HAPManualDefinitionBrick brickDefinition, Object jsonValue, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionBlockDataExpressionElementInLibrary brick = (HAPManualDefinitionBlockDataExpressionElementInLibrary)brickDefinition;

		HAPDefinitionDataExpressionStandAlone value = HAPDefinitionDataExpressionStandAlone.parse((JSONObject)jsonValue, this.m_entityParseService);  
		value.setExpression(this.m_dataExpressionParser.parseExpression(value.getExpressionStr()));
		
		brick.setValue(value);
	}
}
