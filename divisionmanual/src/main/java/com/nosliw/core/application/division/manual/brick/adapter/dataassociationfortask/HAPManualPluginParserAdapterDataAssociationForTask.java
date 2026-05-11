package com.nosliw.core.application.division.manual.brick.adapter.dataassociationfortask;

import org.json.JSONObject;

import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.dataassociation.definition.HAPDefinitionDataAssociationForTask;
import com.nosliw.core.application.common.dataassociation.definition.HAPDefinitionParserDataAssociationForTask;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpSimple;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPManualPluginParserAdapterDataAssociationForTask  extends HAPManualDefinitionPluginParserBrickImpSimple{

	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManualPluginParserAdapterDataAssociationForTask(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan, HAPServiceParseEntity entityParseService) {
		super(HAPEnumBrickType.DATAASSOCIATIONFORTASK_100, HAPManualDefinitionAdapterDataAssociationForTask.class, manualDivisionEntityMan, brickMan);
		this.m_entityParseService = m_entityParseService;
	}
	
	@Override
	protected void parseSimpleDefinitionContentJson(HAPManualDefinitionBrick brickManual, Object jsonValue, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionAdapterDataAssociationForTask entity = (HAPManualDefinitionAdapterDataAssociationForTask)brickManual;
		
		JSONObject daJsonObj =  ((JSONObject)jsonValue).optJSONObject(HAPManualDefinitionAdapterDataAssociationForTask.DEFINITION);
		HAPDefinitionDataAssociationForTask dataAssociationForTask = HAPDefinitionParserDataAssociationForTask.parse(daJsonObj, this.m_entityParseService);
		entity.setDataAssciation(dataAssociationForTask);
	}
}
