package com.nosliw.core.application.entity.app.expressionbuild;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperand;

public interface HAPDataBuildOperand extends HAPEntityParsable{


	public static HAPDefinitionOperand parseOperandDefinition(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPDefinitionOperand)parseService.parseEntityJSONImplicitAttribute(jsonObj, HAPDefinitionOperand.TYPE, HAPDataBuildOperand__HAPEntityParsable.PARSABLEENTITYDOMAIN);
	}
	
}
