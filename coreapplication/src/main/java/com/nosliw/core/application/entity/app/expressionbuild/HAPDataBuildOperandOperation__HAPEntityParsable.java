package com.nosliw.core.application.entity.app.expressionbuild;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandOperation__HAPEntityParsable;

public class HAPDataBuildOperandOperation__HAPEntityParsable extends HAPDataBuildOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_OPERATION;    }
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDataBuildOperandOperation out = new HAPDataBuildOperandOperation();
		HAPDefinitionOperandOperation__HAPEntityParsable.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

