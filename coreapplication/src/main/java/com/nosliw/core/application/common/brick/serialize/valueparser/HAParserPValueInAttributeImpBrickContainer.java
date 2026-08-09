package com.nosliw.core.application.common.brick.serialize.valueparser;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainer;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;

public class HAParserPValueInAttributeImpBrickContainer implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		if(attrName.equals(HAPBrickContainer.ATTRINDEX)) {
			return obj;
		}
		return null;
	}

}
