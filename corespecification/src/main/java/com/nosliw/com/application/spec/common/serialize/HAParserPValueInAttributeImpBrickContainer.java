package com.nosliw.com.application.spec.common.serialize;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;
import com.nosliw.core.application.spec.brick.container.HAPBrickContainer;

public class HAParserPValueInAttributeImpBrickContainer implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		if(attrName.equals(HAPBrickContainer.ATTRINDEX)) {
			return obj;
		}
		return null;
	}

}
