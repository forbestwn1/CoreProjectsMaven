package com.nosliw.core.application.entity.datarule.mandatory;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPParserDataRule;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

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
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.DATARULE_TYPE_MANDATORY;   }

}
