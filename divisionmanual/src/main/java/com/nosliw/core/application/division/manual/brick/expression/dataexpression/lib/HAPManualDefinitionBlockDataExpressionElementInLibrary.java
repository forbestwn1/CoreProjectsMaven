package com.nosliw.core.application.division.manual.brick.expression.dataexpression.lib;

import com.nosliw.core.application.common.dataexpression.definition.HAPDefinitionDataExpressionStandAlone;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrickWithEntityInfo;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.expression.dataexpression.library.HAPBlockDataExpressionElementInLibrary;

public class HAPManualDefinitionBlockDataExpressionElementInLibrary extends HAPManualDefinitionBrickWithEntityInfo{

	public HAPManualDefinitionBlockDataExpressionElementInLibrary() {
		super(HAPEnumBrickType.DATAEXPRESSIONLIBELEMENT_100);
	}

	public HAPDefinitionDataExpressionStandAlone getValue() {   return (HAPDefinitionDataExpressionStandAlone)this.getAttributeValueOfValue(HAPBlockDataExpressionElementInLibrary.VALUE);      }
	public void setValue(HAPDefinitionDataExpressionStandAlone value) {     this.setAttributeValueWithValue(HAPBlockDataExpressionElementInLibrary.VALUE, value);      }
	
}
