package com.nosliw.core.application.division.manual.brick.module;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.container.HAPBrickContainer;
import com.nosliw.core.application.brick.module.HAPBlockModule;
import com.nosliw.core.application.division.manual.brick.test.complex.testcomplex1.HAPManualBlockTestComplex1;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

public class HAPManualBlockModule extends HAPManualBrickImp implements HAPBlockModule{

	public HAPManualBlockModule() {
		super(HAPEnumBrickType.MODULE_100);
	}

	@Override
	public HAPBrickContainer getTasks() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.TASK);   }   

	@Override
	public HAPBrickContainer getCommands() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.COMMAND);   }

	@Override
	public HAPBrickContainer getPages() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.PAGE);   }

}

@Component
class HAPManualBlockModule_parser extends HAPManualBrick_parser{

	public HAPManualBlockModule_parser(HAPManagerApplicationBrick brickManager) {
		super(brickManager, HAPManualBlockTestComplex1.class, HAPEnumBrickType.MODULE_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBlockModule out = new HAPManualBlockModule();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}
}
