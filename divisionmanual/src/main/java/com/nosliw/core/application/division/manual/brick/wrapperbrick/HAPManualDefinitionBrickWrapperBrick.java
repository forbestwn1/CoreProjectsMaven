package com.nosliw.core.application.division.manual.brick.wrapperbrick;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrickWithEntityInfo;

public class HAPManualDefinitionBrickWrapperBrick extends HAPManualDefinitionBrickWithEntityInfo{

	public static final String BRICKTYPE = "brickType";
	
	public HAPManualDefinitionBrickWrapperBrick() {
		super(HAPEnumBrickType.WRAPPERBRICK_100);
	}

}
