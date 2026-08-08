package com.nosliw.core.application.entity.datarule;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public interface HAPDataRuleImplementation extends HAPSerializable, HAPEntityParsable{

	public static final String DOMAIN_PARSER = "datarule.implmentation";
	
	@HAPAttribute
    public static final String TYPE = "type";

	String getImmplementationType();
	
	public static HAPDataRuleImplementation parseDataRuleImplementation(Object obj, HAPServiceParseEntity parseService) {
		return (HAPDataRuleImplementation)parseService.parseEntityJSONImplicitAttribute((JSONObject)obj, TYPE, DOMAIN_PARSER);
	}
	
}

abstract class HAPDataRuleImplementation_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {     return HAPDataRuleImplementation.DOMAIN_PARSER;   }

}
