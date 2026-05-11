package com.nosliw.core.application.entity.datarule.mandatory;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPPluginParserDataRule;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@Component
public class HAPPluginParserDataRuleMandatory extends HAPParserEntityImpWithDomain implements HAPPluginParserDataRule{

	@Override
	public HAPDataRule parse(Object obj) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPDataRuleMandatory out = new HAPDataRuleMandatory();
		return out;
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPDataRuleMandatory out = new HAPDataRuleMandatory();
		return out;
	}

	@Override
	public String getDomain() {   return HAPDataRule.ENTITYPARSEDOMAIN;  }

	@Override
	public String getSubName() {   return HAPConstantShared.DATARULE_TYPE_MANDATORY;   }

}
