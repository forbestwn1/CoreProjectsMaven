package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import com.nosliw.core.application.common.valuecontext.HAPWithValueContext;
import com.nosliw.core.application.division.manual.core.HAPManualEnumBrickType;
import com.nosliw.core.application.spec.HAPEnumBrickType;

public class HAPManualDefinitionBlockComplexUIPage extends HAPManualDefinitionBlockComplxWithUIContent{

	public HAPManualDefinitionBlockComplexUIPage() {
		super(HAPEnumBrickType.UIPAGE_100);
	}

	@Override
	public void init() {
		this.setAttributeValueWithBrick(HAPWithValueContext.VALUECONTEXT, this.getManualBrickManager().newBrickDefinition(HAPManualEnumBrickType.VALUECONTEXT_100));
	}
	
}
