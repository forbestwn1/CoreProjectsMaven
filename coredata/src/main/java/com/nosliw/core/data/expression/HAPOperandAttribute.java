package com.nosliw.core.data.expression;

import com.nosliw.common.constant.HAPAttribute;

public interface HAPOperandAttribute extends HAPOperand{

	@HAPAttribute
	public static final String ATTRIBUTE = "attribute";
	
	@HAPAttribute
	public static final String BASEDATA = "baseData";
	
	String getAttribute();
	
	HAPOperand getBase();

}
