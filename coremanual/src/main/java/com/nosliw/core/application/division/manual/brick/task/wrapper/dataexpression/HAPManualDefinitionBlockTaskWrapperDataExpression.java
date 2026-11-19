package com.nosliw.core.application.division.manual.brick.task.wrapper.dataexpression;

import com.nosliw.core.application.common.dataexpression.definition.HAPDefinitionDataExpressionStandAlone;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.task.wrapper.dataexpression.HAPBlockTaskWrapperDataExpression;

public class HAPManualDefinitionBlockTaskWrapperDataExpression extends HAPManualDefinitionBrick{

	public HAPManualDefinitionBlockTaskWrapperDataExpression() {
		super(HAPEnumBrickType.TASKWRAPPERDATAEXPRESSION);
	}

	public HAPDefinitionDataExpressionStandAlone getDataExpression() {   return (HAPDefinitionDataExpressionStandAlone)this.getAttributeValueOfValue(HAPBlockTaskWrapperDataExpression.DATAEXPRESSION);      }
	public void setDataExpression(HAPDefinitionDataExpressionStandAlone value) {     this.setAttributeValueWithValue(HAPBlockTaskWrapperDataExpression.DATAEXPRESSION, value);      }
	
}
