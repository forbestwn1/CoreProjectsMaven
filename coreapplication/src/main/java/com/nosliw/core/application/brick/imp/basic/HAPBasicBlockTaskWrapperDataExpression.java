package com.nosliw.core.application.brick.imp.basic;

import org.springframework.stereotype.Component;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.task.wrapper.dataexpression.HAPBlockTaskWrapperDataExpression;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBlockDataTaskWrapperDataExpression;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;

public class HAPBasicBlockTaskWrapperDataExpression extends HAPBasicBrick implements HAPBlockTaskWrapperDataExpression{

	public HAPBasicBlockTaskWrapperDataExpression() {
		super(HAPEnumBrickType.TASKWRAPPERDATAEXPRESSION);
		this.setAttributeValueWithValue(DATAEXPRESSION, new HAPDataExpressionStandAlone());;
	}
	
	@Override
	public HAPDataExpressionStandAlone getDataExpression(){	return (HAPDataExpressionStandAlone)this.getAttributeValueOfValue(DATAEXPRESSION);	}
	
}

@Component
class HAPBasicBlockDataTaskWrapperDataExpression_parser extends HAPBasicBrick_parser{

	public HAPBasicBlockDataTaskWrapperDataExpression_parser() {
		super(HAPBasicBlockTaskWrapperDataExpression.class, HAPEnumBrickType.TASKWRAPPERDATAEXPRESSION);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockDataTaskWrapperDataExpression());
	}
	
}
