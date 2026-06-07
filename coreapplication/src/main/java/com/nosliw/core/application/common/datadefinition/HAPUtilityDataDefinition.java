package com.nosliw.core.application.common.datadefinition;

import com.nosliw.common.utils.HAPConstantShared;

public class HAPUtilityDataDefinition {

	public static HAPDataDefinitionWritable toWritableDataDefinition(HAPDataDefinition dataDefinition) {
		String type = dataDefinition.getType();
		if(type.equals(HAPConstantShared.DATADEFINITION_TYPE_READONLY)) {
			return new HAPDataDefinitionWritable(dataDefinition);
		}
		else {
			return (HAPDataDefinitionWritable)dataDefinition;
		}
		
	}
	
	
}
