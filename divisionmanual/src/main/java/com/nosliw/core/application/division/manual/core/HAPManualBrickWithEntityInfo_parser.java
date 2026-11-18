package com.nosliw.core.application.division.manual.core;

import com.nosliw.com.application.spec.common.serialize.HAParserPValueInAttributeImpEntityInfo;
import com.nosliw.core.application.common.brick.HAPIdBrickType;

public class HAPManualBrickWithEntityInfo_parser extends HAPManualBrick_parser{

	public HAPManualBrickWithEntityInfo_parser(Class<? extends HAPManualBrick> manualBrickClass, HAPIdBrickType brickTypeId) {
		super(manualBrickClass, brickTypeId);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpEntityInfo());
	}

}
