package com.nosliw.core.application.entity.datarule;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.com.application.spec.common.interactive.HAPUtilityInteractiveTaskValuePort;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.brick.HAPDomainValueStructure;
import com.nosliw.core.application.common.datarule.HAPDataRule;
import com.nosliw.core.application.common.datarule.HAPUtilityDataRule;
import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveExpression;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveTask;
import com.nosliw.core.application.spec.imp.basic.HAPBasicBlockInteractiveInterfaceExpression;
import com.nosliw.core.application.spec.imp.basic.HAPBasicBlockInteractiveInterfaceTask;

public abstract class HAPPluginTransformerDataRuleImp implements HAPPluginTransformerDataRule{

	protected void buildInterfaceTask(HAPDataRule dataRule, HAPWithBlockInteractiveTask withTaskBrick, HAPDomainValueStructure valueStructureDomian) {
		HAPBasicBlockInteractiveInterfaceTask taskInterfaceBlock = new HAPBasicBlockInteractiveInterfaceTask();
		taskInterfaceBlock.setValue(this.buildValuePortGroupForRuleTaskBrickTask(dataRule, (HAPBrick)withTaskBrick, valueStructureDomian));
		withTaskBrick.setTaskInterface(taskInterfaceBlock);
	}
	
	protected void buildInterfaceExpression(HAPDataRule dataRule, HAPWithBlockInteractiveExpression withExpressionBrick, HAPDomainValueStructure valueStructureDomian) {
		HAPBasicBlockInteractiveInterfaceExpression expressionInterfaceBlock = new HAPBasicBlockInteractiveInterfaceExpression();
		expressionInterfaceBlock.setValue(this.buildValuePortGroupForRuleTaskBrickExpression(dataRule, (HAPBrick)withExpressionBrick, valueStructureDomian));
		withExpressionBrick.setExpressionInterface(expressionInterfaceBlock);
	}
	
	protected HAPInteractiveTask buildValuePortGroupForRuleTaskBrickTask(HAPDataRule dataRule, HAPBrick brick, HAPDomainValueStructure valueStructureDomian) {
		HAPInteractiveTask interactive = HAPUtilityDataRule.buildTaskInterface(dataRule.getDataCriteria());
		HAPUtilityInteractiveTaskValuePort.buildValuePortGroupForInteractiveTask(Pair.of(brick.getInternalValuePorts(), brick.getExternalValuePorts()), interactive, valueStructureDomian);
		return interactive;
	}

	protected HAPInteractiveExpression buildValuePortGroupForRuleTaskBrickExpression(HAPDataRule dataRule, HAPBrick brick, HAPDomainValueStructure valueStructureDomian) {
//		HAPInteractiveExpression interactive = HAPUtilityDataRule.buildExpressionInterface(dataRule.getDataCriteria());
//		HAPUtilityInteractiveTaskValuePort.buildValuePortGroupForInteractiveExpression(Pair.of(brick.getInternalValuePorts(), brick.getExternalValuePorts()), interactive, valueStructureDomian);
//		return interactive;
		return null;
	}
	
}
