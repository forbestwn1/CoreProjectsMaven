package com.nosliw.core.application.entity.datarule;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.datarule.HAPDataRule;

public interface HAPPluginTransformerDataRule {

	HAPEntityOrReference transformDataRule(HAPDataRule dataRule, HAPBundleForBrick bundle);

}
