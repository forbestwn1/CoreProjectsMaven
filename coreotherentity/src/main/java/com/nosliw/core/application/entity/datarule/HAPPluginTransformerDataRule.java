package com.nosliw.core.application.entity.datarule;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.core.application.HAPDomainValueStructure;
import com.nosliw.core.application.entity.datarule.HAPDataRule;

public interface HAPPluginTransformerDataRule {

	HAPEntityOrReference transformDataRule(HAPDataRule dataRule, HAPDomainValueStructure valueStructureDomian);

}
