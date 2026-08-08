package com.nosliw.core.application.entity.datarule;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;

public abstract class HAPParserDataRule  extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPDataRule.ENTITYPARSEDOMAIN;  }
	
	protected void parseToDataRule(JSONObject dataRuleJsonObj, HAPDataRule dataRule, HAPServiceParseEntity parseService) {
		
		String criteriaStr =(String)dataRuleJsonObj.opt(HAPDataRule.CRITERIA);
		if(criteriaStr!=null) {
			dataRule.setDataCriteria(HAPUtilityCriteria.parseCriteria(criteriaStr));
		}
	
		dataRule.setImplementation(HAPDataRuleImplementation.parseDataRuleImplementation(dataRuleJsonObj.opt(HAPDataRule.IMPLEMENTATION), parseService));
	}
	
}
