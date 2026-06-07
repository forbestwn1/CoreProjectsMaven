package com.nosliw.core.application.division.story.design.change;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinitionWritable;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmRequest;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmResponse;
import com.nosliw.core.application.common.interactive.HAPInteractiveResultTask;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIO;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithConstant;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithVariable;
import com.nosliw.core.application.division.story.definition.HAPStoryReferenceElement;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryCommand;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryConstant;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOConstant;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOVariable;
import com.nosliw.core.application.division.story.design.HAPStoryDesignSessionChange;
import com.nosliw.core.data.HAPData;

public class HAPStoryChangeUtility {

	public static HAPStoryChangeItemNew buildNewAppendConstantChange(HAPStoryDesignSessionChange changeSession, HAPStoryReferenceElement parentRef, HAPData constantData, HAPEntityInfo entityInfo) {
		//variable element
		HAPStoryElementAccessoryConstant constantEle = new HAPStoryElementAccessoryConstant(entityInfo);
		HAPStoryChangeItemNew newConstantChange = changeSession.addChangeItemNew(constantEle);
		changeSession.addChangeConnectionNew(parentRef, newConstantChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementWithConstant.getAddConstantChildPath()));

		//end point element
		HAPStoryElementEndPointIO endPointEle = new HAPStoryElementEndPointIOConstant(constantData);
		HAPStoryChangeItemNew newEndpointChange = changeSession.addChangeItemNew(endPointEle);
		changeSession.addChangeConnectionNew(newConstantChange.getElementId(), newEndpointChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath(HAPStoryElementWithEndPoint.CHILD_ENDPOINT)));

		return newConstantChange;
	}
	
	public static HAPStoryChangeItemNew buildNewAppendVariableChange(HAPStoryDesignSessionChange changeSession, HAPStoryReferenceElement parentRef, HAPDataDefinitionWritable dataDefinition, HAPEntityInfo variableInfo) {
		//new variable element
		HAPStoryChangeItemNew newVariableChange = buildNewVariableChange(changeSession, dataDefinition, variableInfo, HAPConstantShared.IO_DIRECTION_BOTH);
		
		//append to parent
		changeSession.addChangeConnectionNew(parentRef, newVariableChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementWithVariable.getAddVariableChildPath()));

		return newVariableChange;
	}

	public static HAPStoryChangeItemNew buildNewVariableChange(HAPStoryDesignSessionChange changeSession, HAPDataDefinition dataDefinition, HAPEntityInfo variableInfo, String ioDirection) {
		//variable element
		HAPStoryElementAccessoryVariable variableEle = new HAPStoryElementAccessoryVariable(variableInfo);
		HAPStoryChangeItemNew newVariableChange = changeSession.addChangeItemNew(variableEle);

		//end point element
		HAPStoryElementEndPointIO endPointEle = new HAPStoryElementEndPointIOVariable(dataDefinition, ioDirection);
		HAPStoryChangeItemNew newEndpointChange = changeSession.addChangeItemNew(endPointEle);
		changeSession.addChangeConnectionNew(newVariableChange.getElementId(), newEndpointChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath(HAPStoryElementWithEndPoint.CHILD_ENDPOINT)));

		return newVariableChange;
	}


	public static HAPStoryChangeItemNew buildNewCommandChange(HAPStoryDesignSessionChange changeSession, HAPInteractiveTask taskInterface, HAPEntityInfo commandInfo) {
		HAPStoryChangeItemNew newCommandChange = changeSession.addChangeItemNew(new HAPStoryElementAccessoryCommand(taskInterface, commandInfo));
		
		//build request end point in command
		for(HAPDefinitionParmRequest parmDef : taskInterface.getRequestParms()) {
			HAPStoryChangeItemNew newParmChange = buildNewVariableChange(changeSession, parmDef.getDataDefinition(), parmDef, HAPConstantShared.IO_DIRECTION_IN);
			changeSession.addChangeConnectionNew(newCommandChange.getElementId(), newParmChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementAccessoryCommand.getAddRequestParmChildPath()));
		}
		
		//build response end point in command
		for(HAPInteractiveResultTask result : taskInterface.getResult()) {
			for(HAPDefinitionParmResponse output : result.getOutput()) {
				HAPStoryChangeItemNew newParmChange = buildNewVariableChange(changeSession, output.getDataDefinition(), output, HAPConstantShared.IO_DIRECTION_OUT);
				changeSession.addChangeConnectionNew(newCommandChange.getElementId(), newParmChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementAccessoryCommand.getAddResponseParmChildPath(result.getName(), output.getName())));
			}
		}
		
		return newCommandChange;
	}
	
}
