package com.nosliw.core.application.division.manual.brick.test.complex.testcomplex1;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.test.complex.testcomplex1.HAPBlockTestComplex1;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPManualBlockTestComplex1 extends HAPManualBrickImp implements HAPBlockTestComplex1{

	public HAPManualBlockTestComplex1() {
		super(HAPEnumBrickType.TEST_COMPLEX_1_100);
	}

	@Override
	public void init() {
		super.init();
	}

}

@Component
class HAPManualBlockTestComplex1_parser extends HAPManualBrick_parser{

	public HAPManualBlockTestComplex1_parser(HAPManagerApplicationBrick brickManager) {
		super(brickManager, HAPManualBlockTestComplex1.class, HAPEnumBrickType.TEST_COMPLEX_1_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBlockTestComplex1 out = new HAPManualBlockTestComplex1();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}
}
