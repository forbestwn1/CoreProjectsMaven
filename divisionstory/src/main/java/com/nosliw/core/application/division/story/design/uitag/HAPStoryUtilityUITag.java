package com.nosliw.core.application.division.story.design.uitag;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplate;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.HAPIdBrickInBundle;
import com.nosliw.core.application.common.command.HAPCommandDefinition;
import com.nosliw.core.application.common.command.HAPCommandHandlerReferenceCommand;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinitionWritableWithInit;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmRequest;
import com.nosliw.core.application.common.datadefinition.HAPUtilityDataDefinition;
import com.nosliw.core.application.common.event.HAPEventDefinition;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.manual.core.standalone.HAPManualManangerStandalone;
import com.nosliw.core.application.division.manual.core.standalone.HAPStandaloneDefinition;
import com.nosliw.core.application.entity.uitag.HAPManagerUITag;
import com.nosliw.core.application.entity.uitag.HAPUITagInfo;
import com.nosliw.core.application.entity.uitag.HAPUITageQueryData;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPStoryUtilityUITag {

	public static HAPBundleForBrick buildStandaloneBundleForUITag(HAPUITageQueryData dataUITagQuery, HAPManagerUITag uiTagMan, HAPManualManangerStandalone standaloneMan, HAPRuntimeInfo runtimeInfo) {
		HAPUITagInfo uiTagInfo = uiTagMan.getDefaultUITagData(dataUITagQuery);

		HAPDataDefinition dataDefinition = dataUITagQuery.getDataDefinition();

		String dataVariableName = "data";
		String tagAlias = "tagAlias";
		
		StringBuffer attContent = new StringBuffer();
		Map<String, String> attributes = new LinkedHashMap<>(uiTagInfo.getAttributes());
		attributes.put(uiTagInfo.getAttributeForData(), dataVariableName);
		for(String name : attributes.keySet()) {
			attContent.append(name + "=\"" + attributes.get(name) + "\" ");
		}

		String initDataStr = "";
		HAPData initData = HAPUtilityDataDefinition.getInitData(dataDefinition);
		if(initData!=null) {
			initDataStr = "\"" + dataVariableName + "\"" + ":" + initData.toStringValue(HAPSerializationFormat.JSON);
		}
		
		String content = new  HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryUtilityUITag.class, "uitag.html"))
		     .setParm("tagName", uiTagInfo.getName())
		     .setParm("dataVariableName", dataVariableName)
		     .setParm("attributes", attContent.toString())
		     .setParm("dataDefinition", dataDefinition.toStringValue(HAPSerializationFormat.JSON))
		     .setParm("initData", initDataStr)
		     .setParm("tagAlias", tagAlias)
		     .getContent();
		
		
		HAPStandaloneDefinition standAloneDef = new HAPStandaloneDefinition(content, HAPSerializationFormat.HTML, HAPEnumBrickType.UIPAGE_100);

		//events
		{
			HAPEventEmitter eventEmitter = new HAPEventEmitter();
			HAPIdBrickInBundle emitterBrickId = new HAPIdBrickInBundle();
			emitterBrickId.setAlias(tagAlias);
			eventEmitter.setEmitterBrickId(emitterBrickId);
			
			HAPEventDefinition eventDef = new HAPEventDefinition();
			eventDef.setName(HAPConstantShared.EVENT_UI_VALUE_CHANGE);
			eventEmitter.setEventDefinition(eventDef);
			standAloneDef.addExposeEvent(eventEmitter);
		}
		
		{
			HAPEventEmitter eventEmitter = new HAPEventEmitter();
			HAPIdBrickInBundle emitterBrickId = new HAPIdBrickInBundle();
			emitterBrickId.setAlias(tagAlias);
			eventEmitter.setEmitterBrickId(emitterBrickId);
			
			HAPEventDefinition eventDef = new HAPEventDefinition();
			eventDef.setName(HAPConstantShared.ERROR_VALIDATION_VALUE);
			eventEmitter.setEventDefinition(eventDef);
			standAloneDef.addExposeEvent(eventEmitter);
		}
		
		//command
		{
			HAPCommandProcess commandProcess = new HAPCommandProcess();
			
			String commandName = "setData";
			
			HAPCommandHandlerReferenceCommand handler = new HAPCommandHandlerReferenceCommand();
			handler.setCommandName(commandName);
			HAPIdBrickInBundle commandHandlerBrickId = new HAPIdBrickInBundle();
			commandHandlerBrickId.setAlias(tagAlias);
			handler.setBrickId(commandHandlerBrickId);
			commandProcess.setCommandHandler(handler);
			
			HAPCommandDefinition commandDef = new HAPCommandDefinition();
			commandDef.setName(commandName);

			HAPDefinitionParmRequest requestParm = new HAPDefinitionParmRequest();
			requestParm.setName("data");
			requestParm.setDataDefinition(new HAPDataDefinitionWritableWithInit(dataDefinition));

			HAPInteractiveTask taskInteractive = new HAPInteractiveTask(List.of(requestParm), null);
			commandDef.setTaskInterface(taskInteractive);
			
			commandProcess.setCommandDefinition(commandDef);
			standAloneDef.addExposeCommand(commandProcess);
		}

		return standaloneMan.buildStandalone(standAloneDef, runtimeInfo);
	}
	
}
