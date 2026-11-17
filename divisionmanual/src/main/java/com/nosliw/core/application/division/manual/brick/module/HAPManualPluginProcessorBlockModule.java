package com.nosliw.core.application.division.manual.brick.module;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlockImp;

public class HAPManualPluginProcessorBlockModule extends HAPManualPluginProcessorBlockImp{

	public HAPManualPluginProcessorBlockModule() {
		super(HAPEnumBrickType.MODULE_100, HAPManualBlockModule.class);
	}

}
