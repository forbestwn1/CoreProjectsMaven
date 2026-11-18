package com.nosliw.core.application.division.manual.brick.wrapperbrick;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.division.manual.core.HAPManualBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualContextProcessBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlockImp;
import com.nosliw.core.application.spec.HAPEnumBrickType;

public class HAPManualPluginProcessorBlockSimpleImpWrapperBrick extends HAPManualPluginProcessorBlockImp{

	public HAPManualPluginProcessorBlockSimpleImpWrapperBrick() {
		super(HAPEnumBrickType.WRAPPERBRICK_100, HAPManualBrickWrapperBrick.class);
	}

	@Override
	public void processInit(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		super.processInit(pathFromRoot, processContext);
		Pair<HAPManualDefinitionBrick, HAPManualBrick> brickInfoPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualDefinitionBrickWrapperBrick def = (HAPManualDefinitionBrickWrapperBrick)brickInfoPair.getLeft();
		HAPManualBrickWrapperBrick exe = (HAPManualBrickWrapperBrick)brickInfoPair.getRight();
		def.cloneToEntityInfo(exe);
	}
}
