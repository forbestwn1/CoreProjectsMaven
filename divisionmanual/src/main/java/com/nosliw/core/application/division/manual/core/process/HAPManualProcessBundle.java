package com.nosliw.core.application.division.manual.core.process;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.HAPWrapperBrickRoot;
import com.nosliw.core.application.division.manual.core.HAPManualContentProvider;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.HAPManualWrapperBrickRoot;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionUtilityParserBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionWrapperBrickRoot;
import com.nosliw.core.application.entity.datarule.HAPManagerDataRule;
import com.nosliw.core.application.entity.datarule.HAPProcessorRuleInBundle;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.expression.definition.HAPParserDataExpression;
import com.nosliw.core.resource.HAPManagerResource;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.runtime.HAPRuntimeManager;

public class HAPManualProcessBundle {

	public static HAPBundleForBrick buildBundle(
			HAPManualContentProvider contentProvider, 
			HAPRuntimeInfo runtimeInfo, 
			HAPManualManagerBrick manualBrickMan, 
			HAPRuntimeManager runtimeMan, 
			HAPManagerApplicationBrick brickManager, 
			HAPDataTypeHelper dataTypeHelper,
			HAPManagerResource resourceMan,
			HAPManagerDataRule dataRuleManager,
			HAPParserDataExpression dataExpressionParser
			) {
		HAPBundleForBrick bundle = new HAPBundleForBrick();
		bundle.setDynamicInfo(contentProvider.getDynamicDefinition());

		Map<String, HAPManualDefinitionWrapperBrickRoot> definitions = new LinkedHashMap();
		
		//branches
		Map<String, HAPManualInfoContent> branchsContent = contentProvider.getBranchContents();
		for(String branchName : branchsContent.keySet()) {
			HAPManualWrapperBrickRoot rootBrick = (HAPManualWrapperBrickRoot)createRootBrick(branchsContent.get(branchName), contentProvider, new HAPManualContextProcessBrick(bundle, branchName, manualBrickMan, brickManager, dataTypeHelper, resourceMan, runtimeInfo), manualBrickMan, runtimeMan, dataExpressionParser);
			definitions.put(rootBrick.getName(), rootBrick.getDefinition());
		}
	
		//main 
		{
			HAPManualWrapperBrickRoot rootBrick = (HAPManualWrapperBrickRoot)createRootBrick(contentProvider.getMainContent(), contentProvider, new HAPManualContextProcessBrick(bundle, HAPConstantShared.NAME_ROOTBRICK_MAIN, manualBrickMan, brickManager, dataTypeHelper, resourceMan, runtimeInfo), manualBrickMan, runtimeMan, dataExpressionParser);
			definitions.put(rootBrick.getName(), rootBrick.getDefinition());
		}

		//process alias
		Map<String, HAPPath> aliasMapping = new LinkedHashMap<String, HAPPath>();
		HAPManualUtilityProcessAlias.processBrickAlias(new HAPManualContextProcessBrick(bundle, HAPConstantShared.NAME_ROOTBRICK_MAIN, manualBrickMan, brickManager, dataTypeHelper, resourceMan, runtimeInfo));
		for(String branchName : bundle.getBranchNames()) {
			HAPManualUtilityProcessAlias.processBrickAlias(new HAPManualContextProcessBrick(bundle, branchName, manualBrickMan, brickManager, dataTypeHelper, resourceMan, runtimeInfo));
		}
		
		//process root bricks
		{
			HAPManualContextProcessBrick cp = new HAPManualContextProcessBrick(bundle, HAPConstantShared.NAME_ROOTBRICK_MAIN, manualBrickMan, brickManager, dataTypeHelper, resourceMan, runtimeInfo);
			HAPManualProcessBrick.processRootBrick(cp);
			for(String branchName : bundle.getBranchNames()) {
				cp = new HAPManualContextProcessBrick(bundle, branchName, manualBrickMan, brickManager, dataTypeHelper, resourceMan, runtimeInfo);
				HAPManualProcessBrick.processRootBrick(cp);
			}
		}
		
		//process data rule
		HAPProcessorRuleInBundle.process(bundle, dataRuleManager, brickManager, runtimeInfo);
		
		bundle.setExtraData(definitions);
		return bundle;
	}
	
	private static HAPWrapperBrickRoot createRootBrick(HAPManualInfoContent contentInfo, HAPManualContentProvider contentProvider, HAPManualContextProcessBrick processContext, HAPManualManagerBrick manualBrickMan, HAPRuntimeManager runtimeMan, HAPParserDataExpression dataExpressionParser) {
		HAPManualDefinitionContextParse parseContext = new HAPManualDefinitionContextParse(contentProvider, HAPConstantShared.BRICK_DIVISION_MANUAL, manualBrickMan, processContext.getBrickManager());
		//get definition
		HAPManualDefinitionWrapperBrickRoot brickDefWrapper = HAPManualDefinitionUtilityParserBrick.parseBrickDefinitionWrapper(contentInfo.getContent(), contentInfo.getBrickTypeId(), contentInfo.getFormat(), parseContext);
		HAPWrapperBrickRoot out = HAPManualProcessBrick.processRootBrickInit(brickDefWrapper, runtimeMan, dataExpressionParser, processContext);
		return out;
	}
	
	
}
