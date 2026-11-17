package com.nosliw.core.application.division.manual.brick.expression.dataexpression.lib;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlockImp;

public class HAPManualPluginProcessorBlockDataExpressionLibrary extends HAPManualPluginProcessorBlockImp{

	public HAPManualPluginProcessorBlockDataExpressionLibrary() {
		super(HAPEnumBrickType.DATAEXPRESSIONLIB_100, HAPManualBlockDataExpressionLibrary.class);
	}

}
