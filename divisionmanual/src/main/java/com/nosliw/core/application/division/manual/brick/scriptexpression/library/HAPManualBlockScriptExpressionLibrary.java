package com.nosliw.core.application.division.manual.brick.scriptexpression.library;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.spec.expression.scriptexpression.library.HAPBlockScriptExpressionLibrary;
import com.nosliw.core.application.division.manual.brick.container.HAPManualDefinitionBrickContainer;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionAttributeInBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;

public class HAPManualBlockScriptExpressionLibrary extends HAPManualDefinitionBrick{

	public HAPManualBlockScriptExpressionLibrary() {
		super(HAPEnumBrickType.SCRIPTEXPRESSIONLIB_100);
	}
	
	@Override
	protected void init() {
		this.setAttributeValueWithBrick(HAPBlockScriptExpressionLibrary.ITEM, this.getManualBrickManager().newBrickDefinition(HAPEnumBrickType.CONTAINER_100));
	}

	public String addElement(HAPManualBlockScriptExpressionElementInLibrary element) {
		return this.getContainer().addElementWithBrickOrReference(element);
	}
	
	public String addElement(HAPManualDefinitionAttributeInBrick element) {
		return this.getContainer().addElementWithAttribute(element);
	}
	
	private HAPManualDefinitionBrickContainer getContainer() {
		return (HAPManualDefinitionBrickContainer)this.getAttributeValueOfBrick(HAPBlockScriptExpressionLibrary.ITEM);
	}
}
