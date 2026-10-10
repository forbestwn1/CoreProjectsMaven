package com.nosliw.core.application.entity.app.databuild;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperand;

public interface HAPDataBuildOperand extends HAPEntityParsable{

	String getType();

	public static HAPDataBuildOperand parseOperandDefinition(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPDataBuildOperand)parseService.parseEntityJSONImplicitAttribute(jsonObj, HAPDefinitionOperand.TYPE, HAPDataBuildOperand__HAPEntityParsable.PARSABLEENTITYDOMAIN);
	}
	
}
