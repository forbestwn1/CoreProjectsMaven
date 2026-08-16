package com.nosliw.core.application.entity.datarule;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPDataRuleMandatory extends HAPDataRule{

	public HAPDataRuleMandatory() {
		super(HAPConstantShared.DATARULE_TYPE_MANDATORY);
	}

	@Override
	public HAPDataRule cloneDataRule() {
		HAPDataRuleMandatory out = new HAPDataRuleMandatory();
		this.cloneToDataRule(out);
		return out;
	}
}

@Component
class HAPDataRuleMandatory_parser extends HAPParserDataRule{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPDataRuleMandatory out = new HAPDataRuleMandatory();
		this.parseToDataRule(jsonObj, out, parseService);
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.DATARULE_TYPE_MANDATORY;   }

}
