package com.nosliw.core.application.spec.common.serialize;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.spec.brick.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;

public class HAParserPValueInAttributeImpBlockInteractiveInterfaceTask implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName){
		case HAPBlockInteractiveInterfaceTask.VALUE:
		{
			return HAPInteractiveTask.parse((JSONObject)obj, parseService);
		}
		}
		return null;
	}

}
