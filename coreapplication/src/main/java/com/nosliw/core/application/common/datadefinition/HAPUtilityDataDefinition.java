package com.nosliw.core.application.common.datadefinition;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPData;

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
	
    public static HAPData getInitData(HAPDataDefinition dataDefinition) {
    	HAPData out = null;
		if(dataDefinition.getType().equals(HAPConstantShared.DATADEFINITION_TYPE_WRITEABLEWITHINIT)){
			out = ((HAPDataDefinitionWritableWithInit)dataDefinition).getInitData();
		}
		return out;
    }
	
}
