package com.nosliw.core.application.entity.datarule;

import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;

public abstract class HAPParserDataRule  extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPDataRule.ENTITYPARSEDOMAIN;  }
	
}
