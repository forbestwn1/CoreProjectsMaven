package com.nosliw.core.application.common.brick.serialize.valueparser;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.spec.task.wrapper.dataexpression.HAPBlockTaskWrapperDataExpression;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;

public class HAParserPValueInAttributeImpBlockDataTaskWrapperDataExpression implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName) {
		case HAPBlockTaskWrapperDataExpression.DATAEXPRESSION:
			return parseService.parseEntityJSONExplicit((JSONObject)obj, HAPDataExpressionStandAlone.class.getName());
		}
		return null;
	}

}
