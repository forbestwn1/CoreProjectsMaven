package com.nosliw.core.application.spec.imp.basic;

import com.nosliw.com.application.spec.common.serialize.HAParserPValueInAttributeImpEntityInfo;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.brick.HAPIdBrickType;

public abstract class HAPBasicBrickWithEntityInfo_parser extends HAPBasicBrick_parser{

	public HAPBasicBrickWithEntityInfo_parser(Class<? extends HAPBrickImp> brickClass, HAPIdBrickType brickTypeId) {
		super(brickClass, brickTypeId);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpEntityInfo());
	}

}
