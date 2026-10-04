package com.nosliw.core.application.entity.app.expressionbuild;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandConstant__HAPEntityParsable;

@Component
public class HAPDataBuildOperandConstant__HAPEntityParsable extends HAPDataBuildOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_CONSTANT;    }
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDataBuildOperandConstant out = new HAPDataBuildOperandConstant();
		HAPDefinitionOperandConstant__HAPEntityParsable.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
