package com.nosliw.core.application.spec.common.serialize;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;
import com.nosliw.core.application.spec.brick.wrappertask.HAPBlockTaskWrapper;

public class HAParserPValueInAttributeImpBlockTaskWrapper implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName) {
		case HAPBlockTaskWrapper.TASKTYPE:
			return obj;
		}
		return null;     
	}

}
