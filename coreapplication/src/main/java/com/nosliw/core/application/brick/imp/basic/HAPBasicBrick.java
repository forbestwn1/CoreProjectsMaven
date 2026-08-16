package com.nosliw.core.application.brick.imp.basic;

import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.common.brick.HAPBrickImp;

public class HAPBasicBrick extends HAPBrickImp{

	public final static String PARSE_DOMAIN = "brick.basic";

	public HAPBasicBrick(HAPIdBrickType brickTypeId) {
		super(brickTypeId, PARSE_DOMAIN);
	}
	
}
