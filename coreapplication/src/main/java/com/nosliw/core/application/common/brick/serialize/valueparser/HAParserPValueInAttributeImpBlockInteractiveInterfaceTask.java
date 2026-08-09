package com.nosliw.core.application.common.brick.serialize.valueparser;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.spec.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;

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
