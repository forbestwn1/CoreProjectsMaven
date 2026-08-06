package com.nosliw.common.variable;

import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;

abstract public class HAPIdVariable_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {    return HAPIdVariable.PARSER_DOMAIN;   }
	
}
