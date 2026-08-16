package com.nosliw.core.application.division.manual.core.process;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPBrick;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPHandlerDownward;
import com.nosliw.core.application.brick.HAPIdBrickInBundle;
import com.nosliw.core.application.brick.HAPUtilityBrick;
import com.nosliw.core.application.common.event.HAPEventDefinition;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.common.event.HAPEventHandlerReference;
import com.nosliw.core.application.common.event.HAPEventHandlerReferenceTask;
import com.nosliw.core.application.common.event.HAPEventProcess;
import com.nosliw.core.application.division.manual.common.event.HAPManualUtilityEvent;
import com.nosliw.core.application.division.manual.core.HAPManualBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionUtilityBrick;

public class HAPManualUtilityProcessEvent {

	public static void processBrickEvent(HAPManualContextProcessBrick processContext) {

		int[] index = {0};
		
		HAPManualUtilityBrickTraverse.traverseTreeWithLocalBrick(processContext, new HAPHandlerDownward() {

			@Override
			public boolean processBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
				Pair<HAPManualDefinitionBrick, HAPManualBrick> brickPair = HAPManualDefinitionUtilityBrick.getBrickPair(path, bundle);
				
				List<HAPEventProcess> eventProcesses = brickPair.getLeft().getEventProcesses();
				for(HAPEventProcess eventProcess : eventProcesses) {
					HAPEventEmitter eventEmitter = eventProcess.getEventEmitter();
					HAPIdBrickInBundle emitterBrickId = eventEmitter.getEmitterBrickId();
					if(emitterBrickId==null) {
						emitterBrickId = new HAPIdBrickInBundle();
						eventEmitter.setEmitterBrickId(emitterBrickId);
					}
					emitterBrickId.setIdPath(path.toString());
					
					String id = index[0] + "";
					brickPair.getRight().addEventId(id);
					bundle.addEventProcess(id, eventProcess);
					
					index[0] = index[0] + 1;
				}
				
				return true;
			}

			@Override
			public void postProcessBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
			}
		}, null);
	}

	public static void processBrickEventNormalizePath(HAPManualContextProcessBrick processContext) {

		HAPManualUtilityBrickTraverse.traverseTreeWithLocalBrick(processContext, new HAPHandlerDownward() {

			@Override
			public boolean processBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
				HAPBrick brick = HAPUtilityBrick.getDescdentBrickLocal(bundle, path);
				List<String> eventProcessIds = brick.getEventIds();
				for(String eventProcessId : eventProcessIds) {
					HAPEventProcess eventProcess = bundle.getEventProcess(eventProcessId);
					
					//normalize path in handler reference
					HAPEventHandlerReference handler = eventProcess.getEventHandlerReference();
					String handlerType = handler.getHandlerType();
					if(handlerType.equals(HAPConstantShared.EVENT_HANDLERTYPE_TASK)) {
						HAPEventHandlerReferenceTask handlerTask = (HAPEventHandlerReferenceTask)handler;
						HAPManualUtilityProcessBrickPath.normalizeBrickReferenceInBundle(handlerTask.getTaskBrickPackage().getBrickId(), path, true, processContext);
					}

					//normalize path in emitter
					HAPManualUtilityProcessBrickPath.normalizeBrickReferenceInBundle(eventProcess.getEventEmitter().getEmitterBrickId(), path, true, processContext);
				}
				
				return true;
			}

			@Override
			public void postProcessBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
			}
		}, null);
	}
	
	public static void processBrickEventValuePortInHandler(HAPManualContextProcessBrick processContext) {

		for(HAPEventProcess eventProcess : processContext.getCurrentBundle().getEventProcesses().values()) {
			
			HAPEventHandlerReference handler = eventProcess.getEventHandlerReference();
			String handlerType = handler.getHandlerType();
			if(handlerType.equals(HAPConstantShared.EVENT_HANDLERTYPE_TASK)) {
				HAPEventDefinition eventDef = eventProcess.getEventEmitter().getEventDefinition();
				HAPManualUtilityEvent.buildValuePortForEventHandlerTask(eventDef, (HAPEventHandlerReferenceTask)handler, processContext.getRootBrickName(), processContext.getCurrentBundle());
			}
		}
	}
	
}
