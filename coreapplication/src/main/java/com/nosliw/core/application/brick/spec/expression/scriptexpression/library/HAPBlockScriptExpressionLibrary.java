package com.nosliw.core.application.brick.spec.expression.scriptexpression.library;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.brick.imp.basic.HAPBasicBrick;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainer;

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
