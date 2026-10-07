package com.nosliw.core.application.entity.app.databuild;

import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPUtilityDataDefinition;
import com.nosliw.core.data.HAPData;

public class HAPDataBuildUtility {

	public static HAPDataBuild buildDataBuildFromDataDefinition(HAPDataDefinition dataDefinition) {
		
		HAPData initData = HAPUtilityDataDefinition.getInitData(dataDefinition);
		
		HAPDataBuild dataBuild = new HAPDataBuild();
		dataBuild.setDataDefinition(requestParm.getDataDefinition());
		
	}
	
	
}
