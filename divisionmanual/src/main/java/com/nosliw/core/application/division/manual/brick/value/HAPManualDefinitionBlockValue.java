package com.nosliw.core.application.division.manual.brick.value;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.spec.value.HAPBlockValue;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;

public class HAPManualDefinitionBlockValue extends HAPManualDefinitionBrick{

	public HAPManualDefinitionBlockValue() {
		super(HAPEnumBrickType.VALUE_100);
	}

	public Object getValue() {    return this.getAttributeValueOfValue(HAPBlockValue.VALUE);     }

	public void setValue(Object obj) {    this.setAttributeValueWithValue(HAPBlockValue.VALUE, obj);      }
	
}
