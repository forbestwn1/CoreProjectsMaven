package com.nosliw.core.application.common.brick.serialize;

import com.nosliw.common.serialization.HAPServiceParseEntity;

public interface HAParserPValueInAttribute {

	Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService);
	
}
