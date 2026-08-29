package com.nosliw.core.application.entity.datarule;

import java.util.HashSet;
import java.util.Map;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPDomainValueStructure;
import com.nosliw.core.application.brick.HAPUtilityBrick;
import com.nosliw.core.application.brick.HAPWrapperBrickRoot;
import com.nosliw.core.application.brick.imp.basic.HAPBasicBlockTaskWrapper;
import com.nosliw.core.application.brick.imp.basic.HAPBasicBrickContainer;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionDataRule;
import com.nosliw.core.application.common.structure.HAPElementStructure;
import com.nosliw.core.application.common.structure.HAPElementStructureLeafData;
import com.nosliw.core.application.common.structure.HAPElementStructureLeafRelativeForValue;
import com.nosliw.core.application.common.structure.HAPRootInStructure;
import com.nosliw.core.application.common.structure.HAPStructure;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.application.entity.brick.HAPUtilityOtherBrick;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPProcessorRuleInBundle {

	public static void process(HAPBundleForBrick bundle, HAPManagerDataRule dataRuleManager, HAPManagerApplicationBrick brickManager, HAPRuntimeInfo runtimeInfo) {
		
		//build new branch to host data rule tasks
		String validationTaskBranchName = HAPConstantShared.BUNDLEBRANCH_NAME_RULETASKS;
		
		HAPBasicBrickContainer containerBrick = new HAPBasicBrickContainer(); 
		
		HAPDomainValueStructure valueStructureDomain = bundle.getValueStructureDomain();
		Map<String, HAPStructure> structures = valueStructureDomain.getValueStructureDefinitions();
		for(HAPStructure structure : new HashSet<HAPStructure>(structures.values())) {
			Map<String, HAPRootInStructure> roots = structure.getRoots();
			for(String rootName : roots.keySet()) {
				HAPElementStructure ele = roots.get(rootName).getDefinition();

				String eleType = ele.getType();
                if(eleType.equals(HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA)) {
                	HAPElementStructureLeafData dataEle = (HAPElementStructureLeafData)ele;
                	buildRuildForDataElement(dataEle, containerBrick, validationTaskBranchName, bundle, dataRuleManager, brickManager, runtimeInfo);
                }
                if(eleType.equals(HAPConstantShared.CONTEXT_ELEMENTTYPE_RELATIVE_FOR_VALUE)) {
                	HAPElementStructureLeafRelativeForValue forValueElement = (HAPElementStructureLeafRelativeForValue)ele;
                	HAPElementStructureLeafData dataEle = forValueElement.getDefinition();
                	buildRuildForDataElement(dataEle, containerBrick, validationTaskBranchName, bundle, dataRuleManager, brickManager, runtimeInfo);
                }
			}
		}
		
		if(!containerBrick.isEmpty()) {
			HAPWrapperBrickRoot validationRuleTaskBranchRoot = new HAPWrapperBrickRoot(containerBrick);
			validationRuleTaskBranchRoot.setName(validationTaskBranchName);
			bundle.addRootBrickWrapper(validationRuleTaskBranchRoot);
		}
	}
	
	private static void buildRuildForDataElement(HAPElementStructureLeafData dataEle, HAPBasicBrickContainer containerBrick, String validationTaskBranchName, HAPBundleForBrick bundle, HAPManagerDataRule dataRuleManager, HAPManagerApplicationBrick brickManager, HAPRuntimeInfo runtimeInfo) {
    	for(HAPDefinitionDataRule ruleDef : dataEle.getDataDefinition().getRules()) {
    		HAPDataRule rule = ruleDef.getDataRule();
    		HAPDataTypeCriteria ruleCriteria = HAPUtilityCriteria.getChildCriteriaByPath(dataEle.getCriteria(), ruleDef.getPath());
    		rule.setDataCriteria(ruleCriteria);
    		
    		HAPBasicBlockTaskWrapper taskWrapperBrick = new HAPBasicBlockTaskWrapper(); 
    		String attrName = containerBrick.addElementWithBrickOrReference(taskWrapperBrick);
    		HAPDataRuleImplementationLocal dataRuleImp = new HAPDataRuleImplementationLocal(validationTaskBranchName + "."+attrName);
    		rule.setImplementation(dataRuleImp);

    		HAPEntityOrReference ruleTaskBrickOrRef = dataRuleManager.transformDataRule(rule, bundle);
    		taskWrapperBrick.setTask(ruleTaskBrickOrRef);
    		taskWrapperBrick.setTaskType(HAPUtilityOtherBrick.getBrickTaskType(HAPUtilityBrick.getBrickType(ruleTaskBrickOrRef), brickManager));
    	}
	}
}
