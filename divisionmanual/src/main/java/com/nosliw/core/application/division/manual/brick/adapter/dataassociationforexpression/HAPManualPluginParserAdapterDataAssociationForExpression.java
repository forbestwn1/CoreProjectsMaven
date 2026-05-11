package com.nosliw.core.application.division.manual.brick.adapter.dataassociationforexpression;

import org.json.JSONObject;

import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.dataassociation.definition.HAPDefinitionDataAssociationForExpression;
import com.nosliw.core.application.common.dataassociation.definition.HAPDefinitionParserDataAssociationForExpression;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpSimple;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPManualPluginParserAdapterDataAssociationForExpression  extends HAPManualDefinitionPluginParserBrickImpSimple{

	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManualPluginParserAdapterDataAssociationForExpression(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan, HAPServiceParseEntity entityParseService) {
		super(HAPEnumBrickType.DATAASSOCIATIONFOREXPRESSION_100, HAPManualDefinitionAdapterDataAssociationForExpression.class, manualDivisionEntityMan, brickMan);
		this.m_entityParseService = entityParseService;
	}
	
	@Override
	protected void parseSimpleDefinitionContentJson(HAPManualDefinitionBrick brickManual, Object jsonValue, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionAdapterDataAssociationForExpression entity = (HAPManualDefinitionAdapterDataAssociationForExpression)brickManual;
		JSONObject daJsonObj =  ((JSONObject)jsonValue).optJSONObject(HAPManualDefinitionAdapterDataAssociationForExpression.DEFINITION);
		HAPDefinitionDataAssociationForExpression dataAssociationForExpression = HAPDefinitionParserDataAssociationForExpression.parse(daJsonObj, this.m_entityParseService);
		entity.setDataAssciation(dataAssociationForExpression);
	}
}
