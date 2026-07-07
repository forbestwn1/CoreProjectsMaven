package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualEnumBrickType;
import com.nosliw.core.xxx.application1.HAPWithValueContext;

public class HAPManualDefinitionBlockComplexUIPage extends HAPManualDefinitionBlockComplxWithUIContent{

	public HAPManualDefinitionBlockComplexUIPage() {
		super(HAPEnumBrickType.UIPAGE_100);
	}

	@Override
	public void init() {
		this.setAttributeValueWithBrick(HAPWithValueContext.VALUECONTEXT, this.getManualBrickManager().newBrickDefinition(HAPManualEnumBrickType.VALUECONTEXT_100));
	}
	
}
