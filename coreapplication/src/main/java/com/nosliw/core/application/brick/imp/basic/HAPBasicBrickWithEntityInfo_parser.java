package com.nosliw.core.application.brick.imp.basic;

import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpEntityInfo;

public abstract class HAPBasicBrickWithEntityInfo_parser extends HAPBasicBrick_parser{

	public HAPBasicBrickWithEntityInfo_parser(Class<? extends HAPBrickImp> brickClass, HAPIdBrickType brickTypeId) {
		super(brickClass, brickTypeId);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpEntityInfo());
	}

}
