package com.nosliw.core.application.division.manual.brick.expression.dataexpression.standalone;

import com.nosliw.core.application.common.dataexpression.definition.HAPDefinitionDataExpressionStandAlone;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.expression.dataexpression.standalone.HAPBlockDataExpressionStandAlone;

public class HAPManualDefinitionBlockDataExpressionStandAlone extends HAPManualDefinitionBrick{

	public HAPManualDefinitionBlockDataExpressionStandAlone() {
		super(HAPEnumBrickType.DATAEXPRESSIONSTANDALONE_100);
	}

	public HAPDefinitionDataExpressionStandAlone getValue() {   return (HAPDefinitionDataExpressionStandAlone)this.getAttributeValueOfValue(HAPBlockDataExpressionStandAlone.VALUE);      }
	public void setValue(HAPDefinitionDataExpressionStandAlone value) {     this.setAttributeValueWithValue(HAPBlockDataExpressionStandAlone.VALUE, value);      }
	
}
