package com.nosliw.core.application.division.manual.brick.wrapperbrick;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.wrapperbrick.HAPBrickWrapperBrick;
import com.nosliw.core.application.division.manual.core.HAPManualBrickWithEntityInfo;
import com.nosliw.core.application.division.manual.core.HAPManualBrickWithEntityInfo_parser;

public class HAPManualBrickWrapperBrick extends HAPManualBrickWithEntityInfo implements HAPBrickWrapperBrick{

	public static final String INFO = "info";
	
	public HAPManualBrickWrapperBrick() {
		super(HAPEnumBrickType.WRAPPERBRICK_100);
	}

	@Override
	public HAPEntityOrReference getBrick() {   return this.getAttributeValueOfBrick(BRICK);  }

}

@Component
class HAPManualBrickWrapperBrick_parser extends HAPManualBrickWithEntityInfo_parser{

	public HAPManualBrickWrapperBrick_parser() {
		super(HAPManualBrickWrapperBrick.class, HAPEnumBrickType.WRAPPERBRICK_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBrickWrapperBrick out = new HAPManualBrickWrapperBrick();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}

}
