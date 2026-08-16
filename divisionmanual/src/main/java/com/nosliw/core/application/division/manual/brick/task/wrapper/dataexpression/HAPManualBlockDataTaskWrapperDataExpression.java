package com.nosliw.core.application.division.manual.brick.task.wrapper.dataexpression;

import org.springframework.stereotype.Component;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.task.wrapper.dataexpression.HAPBlockTaskWrapperDataExpression;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBlockDataTaskWrapperDataExpression;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

public class HAPManualBlockDataTaskWrapperDataExpression extends HAPManualBrickImp implements HAPBlockTaskWrapperDataExpression{

	public HAPManualBlockDataTaskWrapperDataExpression() {
		super(HAPEnumBrickType.TASKWRAPPERDATAEXPRESSION);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(DATAEXPRESSION, new HAPDataExpressionStandAlone());;
	}
	
	@Override
	public HAPDataExpressionStandAlone getDataExpression(){	return (HAPDataExpressionStandAlone)this.getAttributeValueOfValue(DATAEXPRESSION);	}
	
}

@Component
class HAPManualBlockDataTaskWrapperDataExpression_parser extends HAPManualBrick_parser{

	public HAPManualBlockDataTaskWrapperDataExpression_parser() {
		super(HAPManualBlockDataTaskWrapperDataExpression.class, HAPEnumBrickType.TASKWRAPPERDATAEXPRESSION);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockDataTaskWrapperDataExpression());
	}
	
}
