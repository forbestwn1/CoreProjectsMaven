package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIContent;
import com.nosliw.core.application.common.style.HAPUIStyleUtility;
import com.nosliw.core.application.division.manual.core.HAPManualBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualContextProcessBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlockImp;

public class HAPManualPluginProcessorBlockUIPage extends HAPManualPluginProcessorBlockImp{

	public HAPManualPluginProcessorBlockUIPage() {
		super(HAPEnumBrickType.UIPAGE_100, HAPManualBlockComplexUIPage.class);
	}

	@Override
	public void processInit(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		Pair<HAPManualDefinitionBrick, HAPManualBrick> blockPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualDefinitionBlockComplexUIPage uiPageDef = (HAPManualDefinitionBlockComplexUIPage)blockPair.getLeft();
		HAPManualBlockComplexUIPage uiPageExe = (HAPManualBlockComplexUIPage)blockPair.getRight();
		
		uiPageExe.setAttributeValueWithValue(HAPWithUIContent.STYLE, uiPageDef.getStyle());
	}

	@Override
	public void postProcessBrick(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		
		Pair<HAPManualDefinitionBrick, HAPManualBrick> blockPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualDefinitionBlockComplexUIPage uiPageDef = (HAPManualDefinitionBlockComplexUIPage)blockPair.getLeft();
		HAPManualBlockComplexUIPage uiPageExe = (HAPManualBlockComplexUIPage)blockPair.getRight();
		
		uiPageExe.setStyleScript(HAPUIStyleUtility.process(uiPageExe));
	}

}
