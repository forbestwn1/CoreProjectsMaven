package com.nosliw.core.data.expression;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.core.data.HAPData;

public interface HAPOperandConstant extends HAPOperand{

	@HAPAttribute
	public final static String DATA = "data"; 
	
	HAPData getData();
	
}
