package com.nosliw.core.application.division.manual.brick.module;

import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlockImp;
import com.nosliw.core.application.spec.HAPEnumBrickType;

public class HAPManualPluginProcessorBlockModule extends HAPManualPluginProcessorBlockImp{

	public HAPManualPluginProcessorBlockModule() {
		super(HAPEnumBrickType.MODULE_100, HAPManualBlockModule.class);
	}

}
