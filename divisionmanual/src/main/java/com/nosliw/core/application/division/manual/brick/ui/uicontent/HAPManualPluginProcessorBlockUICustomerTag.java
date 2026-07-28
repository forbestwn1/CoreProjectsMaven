package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIContent;
import com.nosliw.core.application.division.manual.core.HAPManualBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualContextProcessBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlockImp;
import com.nosliw.core.application.entity.uitag.HAPUITagDefinitionAttribute;

public class HAPManualPluginProcessorBlockUICustomerTag extends HAPManualPluginProcessorBlockImp{

	public HAPManualPluginProcessorBlockUICustomerTag() {
		super(HAPEnumBrickType.UICUSTOMERTAG_100, HAPManualBlockComplexUICustomerTag.class);
	}

	@Override
	public void processInit(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		Pair<HAPManualDefinitionBrick, HAPManualBrick> blockPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualDefinitionBlockComplexUICustomerTag uiCustomerTagDef = (HAPManualDefinitionBlockComplexUICustomerTag)blockPair.getLeft();
		HAPManualBlockComplexUICustomerTag uiCustomerTagExe = (HAPManualBlockComplexUICustomerTag)blockPair.getRight();

		uiCustomerTagExe.setAttributeValueWithValue(HAPWithUIContent.STYLE, uiCustomerTagDef.getStyle());

		uiCustomerTagExe.setUITagDefinition(uiCustomerTagDef.getUITagDefinition());
		
		Map<String, String> attrValues = uiCustomerTagDef.getTagAttributes();
		for(String attrName : attrValues.keySet()) {
			uiCustomerTagExe.addTagAttribute(attrName, attrValues.get(attrName));
		}

		Map<String, HAPUITagDefinitionAttribute> attrDefs = uiCustomerTagDef.getTagAttributeDefinitions();
		for(String attrName : attrDefs.keySet()) {
			uiCustomerTagExe.addAttributeDefinition(attrDefs.get(attrName));
		}
		
		uiCustomerTagExe.setUIId(uiCustomerTagDef.getUIId());
		if(uiCustomerTagDef.getBase()!=null) {
			uiCustomerTagExe.setBase(uiCustomerTagDef.getBase());
		}
		
		Map<String, String> metaData = uiCustomerTagDef.getMetaData();
		for(String key : metaData.keySet()) {
			uiCustomerTagExe.addMetaData(key, metaData.get(key));
		}
		uiCustomerTagExe.addMetaData(HAPConstantShared.UITAG_METADATA_UITAGNAME, uiCustomerTagDef.getUITagDefinition().getName());
		uiCustomerTagExe.addMetaData(HAPConstantShared.UITAG_METADATA_UITAGVERSION, uiCustomerTagDef.getUITagDefinition().getVersion());
		

		
		if(uiCustomerTagDef.getScriptResourceId()!=null) {
			uiCustomerTagExe.setScriptResourceId(uiCustomerTagDef.getScriptResourceId());
		}
	}

	@Override
	public void normalizeBrickPath(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		HAPBundleForBrick bundle = processContext.getCurrentBundle();
		Pair<HAPManualDefinitionBrick, HAPManualBrick> blockPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualBlockComplexUICustomerTag executableBlock = (HAPManualBlockComplexUICustomerTag)blockPair.getRight();

	}
	
	@Override
	public void processOtherValuePortBuild(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		super.processOtherValuePortBuild(pathFromRoot, processContext);
		Pair<HAPManualDefinitionBrick, HAPManualBrick> blockPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualDefinitionBlockComplexUICustomerTag blockDef = (HAPManualDefinitionBlockComplexUICustomerTag)blockPair.getLeft();
		HAPManualBlockComplexUICustomerTag executableBlock = (HAPManualBlockComplexUICustomerTag)blockPair.getRight();

	}

}
