package com.nosliw.core.application.common.dataassociation.definition;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPDefinitionParserDataAssociationForExpression {

	public static HAPDefinitionDataAssociationForExpression parse(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		HAPDefinitionDataAssociationForExpression out = new HAPDefinitionDataAssociationForExpression();
		
		JSONObject inputMappingJson = jsonObj.optJSONObject(HAPDefinitionDataAssociationForExpression.IN);
		if(inputMappingJson!=null) {
			HAPDefinitionDataAssociation inDataAssociation = HAPDefinitionParserDataAssociation.buildDefinitionByJson(inputMappingJson, entityParseService);
			inDataAssociation.setDirection(HAPConstantShared.DATAASSOCIATION_DIRECTION_DOWNSTREAM);
			out.setInDataAssociation(inDataAssociation);
		}

		JSONObject outputMappingJson = jsonObj.optJSONObject(HAPDefinitionDataAssociationForExpression.OUT);
		if(outputMappingJson!=null) {
			HAPDefinitionDataAssociation outDataAssociation = HAPDefinitionParserDataAssociation.buildDefinitionByJson(outputMappingJson, entityParseService);
			outDataAssociation.setDirection(HAPConstantShared.DATAASSOCIATION_DIRECTION_UPSTREAM);
			out.setOutDataAssociation(outDataAssociation);
		}
		return out;
	}
	
}
