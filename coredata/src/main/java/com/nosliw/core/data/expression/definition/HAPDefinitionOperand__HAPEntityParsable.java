package com.nosliw.core.data.expression.definition;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPServiceParseEntity;

abstract public class HAPDefinitionOperand__HAPEntityParsable  extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPDefinitionOperand.PARSABLEENTITYDOMAIN;   }

	static void parseToEntity(JSONObject jsonObj, HAPDefinitionOperand operandDefinition, HAPServiceParseEntity parseService) {
	}
	
}
