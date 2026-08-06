package com.nosliw.core.data.expression;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.common.variable.HAPWithVariable;

@HAPEntityWithAttribute
public interface HAPExpressionData extends HAPWithVariable, HAPSerializable, HAPEntityParsable{

	@HAPAttribute
	public static String OPERAND = "operand";
	
	HAPOperand getOperand();

}
