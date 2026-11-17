package com.nosliw.core.application.division.manual.brick.container;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.HAPIdBrickType;

public class HAPManualDefinitionBrickContainer extends HAPManualDefinitionBrickContainerBase{

	public HAPManualDefinitionBrickContainer(HAPIdBrickType childBrickTypeId) {
		super(HAPEnumBrickType.CONTAINER_100, childBrickTypeId);
	}
	
	public HAPManualDefinitionBrickContainer () {
		super(HAPEnumBrickType.CONTAINER_100);
	}
	
}
