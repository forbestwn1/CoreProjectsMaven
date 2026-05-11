package com.nosliw.core.service.entityparse;

import com.nosliw.common.utils.HAPUtilityNamingConversion;

public abstract class HAPParserEntityImpWithDomain implements HAPParserEntity{

    public abstract String getDomain();
	
    public abstract String getSubName();
	
	@Override
	public String getEntityType() {
		return HAPUtilityNamingConversion.cascadePath(getDomain(), getSubName());
	}

}
