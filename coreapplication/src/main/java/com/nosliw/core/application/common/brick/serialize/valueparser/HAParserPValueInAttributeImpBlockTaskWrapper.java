package com.nosliw.core.application.common.brick.serialize.valueparser;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.wrappertask.HAPBlockTaskWrapper;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;

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
