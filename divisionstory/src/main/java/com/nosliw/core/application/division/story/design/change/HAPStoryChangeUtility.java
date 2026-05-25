package com.nosliw.core.application.division.story.design.change;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParm;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionResult;
import com.nosliw.core.application.common.interactive.HAPInteractiveResultTask;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIO;
import com.nosliw.core.application.division.story.definition.HAPStoryReferenceElement;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryCommand;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryConstant;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOConstant;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOVariable;
import com.nosliw.core.application.division.story.design.HAPStoryDesignSessionChange;
import com.nosliw.core.data.HAPData;

public class HAPStoryChangeUtility {

	public static HAPStoryChangeItemNew buildNewConstantChange(HAPStoryDesignSessionChange changeSession, HAPStoryReferenceElement parentRef, HAPData constantData, HAPEntityInfo entityInfo) {
		//variable element
		HAPStoryElementAccessoryConstant constantEle = new HAPStoryElementAccessoryConstant(entityInfo);
		HAPStoryChangeItemNew newConstantChange = changeSession.addChangeItemNew(constantEle);
		changeSession.addChangeConnectionNew(parentRef, newConstantChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath(HAPStoryElementAccessoryConstant.CHILD_ENDPOINT)));

		//end point element
		HAPStoryElementEndPointIO endPointEle = new HAPStoryElementEndPointIOConstant(constantData);
		HAPStoryChangeItemNew newEndpointChange = changeSession.addChangeItemNew(endPointEle);
		changeSession.addChangeConnectionNew(newConstantChange.getElementId(), newEndpointChange.getElementId(), new HAPStoryChangeInfoConnectionContainer());

		return newConstantChange;
	}
	
	public static HAPStoryChangeItemNew buildNewVariableChange(HAPStoryDesignSessionChange changeSession, HAPStoryReferenceElement parentRef, HAPDataDefinition dataDefinition, HAPEntityInfo variableInfo) {
		//variable element
		HAPStoryElementAccessoryVariable variableEle = new HAPStoryElementAccessoryVariable(variableInfo);
		HAPStoryChangeItemNew newVariableChange = changeSession.addChangeItemNew(variableEle);
		changeSession.addChangeConnectionNew(parentRef, newVariableChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath(HAPStoryElementAccessoryVariable.CHILD_ENDPOINT)));

		//end point element
		HAPStoryElementEndPointIO endPointEle = new HAPStoryElementEndPointIOVariable(dataDefinition, HAPConstantShared.IO_DIRECTION_BOTH);
		HAPStoryChangeItemNew newEndpointChange = changeSession.addChangeItemNew(endPointEle);
		changeSession.addChangeConnectionNew(newVariableChange.getElementId(), newEndpointChange.getElementId(), new HAPStoryChangeInfoConnectionContainer());

		return newVariableChange;
	}


	public static HAPStoryChangeItemNew buildNewCommandChange(HAPStoryDesignSessionChange changeSession, HAPInteractiveTask taskInterface, HAPEntityInfo commandInfo) {
		HAPStoryChangeItemNew newCommandChange = changeSession.addChangeItemNew(new HAPStoryElementAccessoryCommand(taskInterface, commandInfo));
		
		//build request end point in command
		for(HAPDefinitionParm parmDef : taskInterface.getRequestParms()) {
			HAPStoryChangeItemNew parmEndpointNew = changeSession.addChangeItemNew(new HAPStoryElementEndPointIOVariable(parmDef.getDataDefinition(), HAPConstantShared.IO_DIRECTION_IN));
			changeSession.addChangeConnectionNew(newCommandChange.getElementId(), parmEndpointNew.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementAccessoryCommand.buildPathForRequestEndPoint(parmDef.getName())));
		}
		
		//build response end point in command
		for(HAPInteractiveResultTask result : taskInterface.getResult()) {
			for(HAPDefinitionResult output : result.getOutput()) {
				HAPStoryChangeItemNew parmEndpointNew = changeSession.addChangeItemNew(new HAPStoryElementEndPointIOVariable(output.getDataDefinition(), HAPConstantShared.IO_DIRECTION_OUT));
				changeSession.addChangeConnectionNew(newCommandChange.getElementId(), parmEndpointNew.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementAccessoryCommand.buildPathForResponseEndPoint(result.getName(), output.getName())));
			}
		}
		
		return newCommandChange;
	}
	


}
