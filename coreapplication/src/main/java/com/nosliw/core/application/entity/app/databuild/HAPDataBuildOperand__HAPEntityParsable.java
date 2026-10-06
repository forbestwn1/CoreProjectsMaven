package com.nosliw.core.application.entity.app.databuild;

import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;

public abstract class HAPDataBuildOperand__HAPEntityParsable extends HAPParserEntityImpWithDomain{

	public static final String PARSABLEENTITYDOMAIN = "core.operand.databuild";

	@Override
	public String getDomain() {    return PARSABLEENTITYDOMAIN;    }

}
