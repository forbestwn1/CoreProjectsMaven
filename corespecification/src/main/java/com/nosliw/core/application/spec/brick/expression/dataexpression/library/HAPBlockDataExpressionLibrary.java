package com.nosliw.core.application.spec.brick.expression.dataexpression.library;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.spec.brick.container.HAPBrickContainer;

@HAPEntityWithAttribute
public interface HAPBlockDataExpressionLibrary extends HAPBrick{

	@HAPAttribute
	public static String ITEM = "item";

	HAPBrickContainer getItems();

}
