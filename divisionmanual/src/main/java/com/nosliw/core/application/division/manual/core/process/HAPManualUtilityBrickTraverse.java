package com.nosliw.core.application.division.manual.core.process;

import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPHandlerBrickWrapper;
import com.nosliw.core.application.brick.HAPHandlerDownward;
import com.nosliw.core.application.brick.HAPWithBrick;
import com.nosliw.core.application.brick.HAPWrapperValue;
import com.nosliw.core.application.division.manual.core.HAPManualUtilityBrick;
import com.nosliw.core.application.entity.brick.HAPUtilityOtherBrickTraverse;

public class HAPManualUtilityBrickTraverse {

	//traverse only leaves that is local complex entity
	public static void traverseTree(HAPManualContextProcessBrick processContext, HAPHandlerDownward processor, Object data) {
		HAPUtilityOtherBrickTraverse.traverseTree(processContext.getCurrentBundle(), processContext.getRootBrickName(), processor, processContext.getBrickManager(), data);
	}

	public static void traverseTreeWithLocalBrick(HAPManualContextProcessBrick processContext, HAPHandlerDownward processor, Object data) {
		HAPUtilityOtherBrickTraverse.traverseTreeWithLocalBrick(processContext.getCurrentBundle(), processContext.getRootBrickName(), processor, processContext.getBrickManager(), data);
	}

	public static void traverseTreeWithLocalBrickComplex(HAPManualContextProcessBrick processContext, HAPHandlerDownward processor, Object data) {
		traverseTreeWithLocalBrick(
				processContext, 
			new HAPHandlerBrickWrapper(processor, true) {
				@Override
				protected boolean isValidAttribute(HAPAttributeInBrick attr) {
					HAPWrapperValue attrValueInfo = attr.getValueWrapper();
					return HAPManualUtilityBrick.isBrickComplex(((HAPWithBrick)attrValueInfo).getBrick().getBrickType(), processContext.getManualBrickManager());
				}
			}, 
			data);
	}
	
}
