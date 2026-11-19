package com.nosliw.com.application.spec.common.serialize;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;
import com.nosliw.core.application.spec.brick.task.wrapper.dataexpression.HAPBlockTaskWrapperDataExpression;

public class HAParserPValueInAttributeImpBlockDataTaskWrapperDataExpression implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName) {
		case HAPBlockTaskWrapperDataExpression.DATAEXPRESSION:
			return parseService.parseEntityJSONExplicit((JSONObject)obj, HAPDataExpressionStandAlone.ENTITYNAMEFORSERIALIZE);
		}
		return null;
	}

}
