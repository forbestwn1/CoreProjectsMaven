package com.nosliw.core.application.spec.brick.expression.scriptexpression.library;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPIdBrickType;
import com.nosliw.core.application.spec.brick.container.HAPBrickContainer;
import com.nosliw.core.application.spec.imp.basic.HAPBasicBrick;

@HAPEntityWithAttribute
public class HAPBlockScriptExpressionLibrary extends HAPBasicBrick{

	public HAPBlockScriptExpressionLibrary(HAPIdBrickType brickTypeId) {
		super(brickTypeId);
		// TODO Auto-generated constructor stub
	}

	@HAPAttribute
	public static String ITEM = "item";

/*
	@Override
	public void init() {	
		this.setAttributeValueWithBrick(ITEM, new HAPBrickContainer());
	}
*/

	public HAPBrickContainer getItems() {
		return (HAPBrickContainer)this.getAttributeValueOfBrickLocal(ITEM);
	}

}
