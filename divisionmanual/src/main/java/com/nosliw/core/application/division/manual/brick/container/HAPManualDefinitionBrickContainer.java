package com.nosliw.core.application.division.manual.brick.container;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.HAPIdBrickType;

public class HAPManualDefinitionBrickContainer extends HAPManualDefinitionBrickContainerBase{

	public HAPManualDefinitionBrickContainer(HAPIdBrickType childBrickTypeId) {
		super(HAPEnumBrickType.CONTAINER_100, childBrickTypeId);
	}
	
	public HAPManualDefinitionBrickContainer () {
		super(HAPEnumBrickType.CONTAINER_100);
	}
	
}
