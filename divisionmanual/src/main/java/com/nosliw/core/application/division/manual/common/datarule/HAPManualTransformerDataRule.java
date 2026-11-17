package com.nosliw.core.application.division.manual.common.datarule;

import com.nosliw.core.application.common.datarule.HAPDataRule;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;

public interface HAPManualTransformerDataRule {

	HAPManualDefinitionBrick transformDataRule(HAPDataRule dataRule);
	
}
