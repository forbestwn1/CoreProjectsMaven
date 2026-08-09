package com.nosliw.core.application.brick.imp.basic;

import com.nosliw.core.application.HAPAdapter;
import com.nosliw.core.application.HAPAdapterImp;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.brick.serialize.HAPParserBrick;

public abstract class HAPBasicBrick_parser extends HAPParserBrick{

	public HAPBasicBrick_parser(Class<? extends HAPBrickImp> brickClass, HAPIdBrickType brickTypeId) {
		super(HAPBasicBrick.PARSE_DOMAIN, brickClass, brickTypeId);
	}

	@Override
	protected HAPAttributeInBrick newAttributeObject() {
		return new HAPAttributeInBrick();
	}

	@Override
	protected HAPAdapter newAdapterObject() {
		return new HAPAdapterImp();
	}

}
